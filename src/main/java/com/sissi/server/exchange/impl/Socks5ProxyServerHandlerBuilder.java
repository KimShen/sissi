package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.socks.SocksAddressType;
import io.netty.handler.codec.socks.SocksAuthScheme;
import io.netty.handler.codec.socks.SocksCmdRequest;
import io.netty.handler.codec.socks.SocksCmdResponse;
import io.netty.handler.codec.socks.SocksCmdStatus;
import io.netty.handler.codec.socks.SocksInitRequest;
import io.netty.handler.codec.socks.SocksInitResponse;
import io.netty.handler.codec.socks.SocksResponse;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Exchanger;
import com.sissi.server.exchange.ExchangerContext;
import com.sissi.server.exchange.ExchangerTerminal;
import com.sissi.server.impl.NetworkTransfer;
import com.sissi.server.netty.ServerHandlerBuilder;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerBuilder implements ServerHandlerBuilder {

	private final AttributeKey<Exchanger> exchanger = AttributeKey.valueOf("exchanger");

	private final String resource = Sock5ProxyServerHandler.class.getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final ExchangerContext exchangerContext;

	private final ResourceCounter resourceCounter;

	private final byte[] init;

	private final byte[] cmd;

	public Socks5ProxyServerHandlerBuilder(BytestreamsProxy proxy, ExchangerContext exchangerContext, ResourceCounter resourceCounter) {
		super();
		this.resourceCounter = resourceCounter;
		this.exchangerContext = exchangerContext;
		this.init = this.prepareStatic(this.buildInit());
		this.cmd = this.prepareStatic(this.buildCmd(proxy));
	}

	private byte[] prepareStatic(ByteBuf buf) {
		byte[] staticInit = new byte[buf.readableBytes()];
		buf.readBytes(staticInit);
		return staticInit;
	}

	private ByteBuf buildInit() {
		ByteBuf buf = Unpooled.buffer();
		new SocksInitResponse(SocksAuthScheme.NO_AUTH).encodeAsByteBuf(buf);
		return buf;
	}

	private ByteBuf buildCmd(BytestreamsProxy proxy) {
		ByteBuf buf = Unpooled.buffer();
		new SocksCmdResponseWrap(proxy, new SocksCmdResponse(SocksCmdStatus.SUCCESS, SocksAddressType.DOMAIN)).encodeAsByteBuf(buf);
		return buf;
	}

	public ChannelHandler build() throws IOException {
		return new Sock5ProxyServerHandler();
	}

	private class Sock5ProxyServerHandler extends ChannelInboundHandlerAdapter {

		public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
			Socks5ProxyServerHandlerBuilder.this.resourceCounter.increment(Socks5ProxyServerHandlerBuilder.this.resource);
		}

		public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
			// Receiver must close source
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().close(ExchangerTerminal.SOURCE);
			Socks5ProxyServerHandlerBuilder.this.resourceCounter.decrement(Socks5ProxyServerHandlerBuilder.this.resource);
		}

		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			if (Socks5ProxyServerHandlerBuilder.this.log.isDebugEnabled()) {
				Socks5ProxyServerHandlerBuilder.this.log.debug(cause.toString());
				cause.printStackTrace();
			}
			ctx.close();
		}

		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			// TODO:
			// Check WRITE or READ
			if (evt.getClass() == IdleStateEvent.class && IdleStateEvent.class.cast(evt).state() == IdleState.WRITER_IDLE) {
				ctx.close();
			}
		}

		public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				this.prepare(ctx, msg, ctx.write(this.build(msg))).flush();
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

		private ChannelHandlerContext prepare(final ChannelHandlerContext ctx, Object msg, ChannelFuture future) throws IOException {
			if (msg.getClass() == SocksCmdRequest.class) {
				SocksCmdRequest cmd = SocksCmdRequest.class.cast(msg);
				return Socks5ProxyServerHandlerBuilder.this.exchangerContext.exists(cmd.host()) ? this.bridge(Socks5ProxyServerHandlerBuilder.this.exchangerContext.leave(cmd.host()), future, ctx) : this.join(cmd, ctx);
			}
			return ctx;
		}

		private ChannelHandlerContext join(SocksCmdRequest cmd, ChannelHandlerContext ctx) throws IOException {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).set(Socks5ProxyServerHandlerBuilder.this.exchangerContext.join(cmd.host(), new NetworkTransfer(ctx)));
			return ctx;
		}

		private ChannelHandlerContext bridge(Exchanger exchanger, ChannelFuture future, ChannelHandlerContext ctx) throws IOException {
			future.addListener(new BridgeExchangeListener(ctx, exchanger));
			return ctx;
		}

		private ByteBuf build(Object msg) {
			return msg.getClass() == SocksInitRequest.class ? Unpooled.wrappedBuffer(Socks5ProxyServerHandlerBuilder.this.init) : Unpooled.wrappedBuffer(Socks5ProxyServerHandlerBuilder.this.cmd);
		}

		private class BridgeExchangeListener implements GenericFutureListener<Future<Void>> {

			private final ChannelHandlerContext ctx;

			private final Exchanger exchanger;

			public BridgeExchangeListener(ChannelHandlerContext ctx, Exchanger exchanger) {
				super();
				this.ctx = ctx;
				this.exchanger = exchanger.source(new ContextCloseable(ctx));
			}

			@Override
			public void operationComplete(Future<Void> future) throws Exception {
				if (future.isSuccess()) {
					ctx.pipeline().remove(IdleStateHandler.class);
					ctx.pipeline().addFirst(new BridgeExchangerServerHandler());
					ctx.pipeline().context(BridgeExchangerServerHandler.class).attr(Socks5ProxyServerHandlerBuilder.this.exchanger).set(this.exchanger);
				}
			}
		}
	}

	@Sharable
	private class BridgeExchangerServerHandler extends ChannelInboundHandlerAdapter {

		public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
			Socks5ProxyServerHandlerBuilder.this.resourceCounter.decrement(Socks5ProxyServerHandlerBuilder.this.resource);
		}

		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			if (Socks5ProxyServerHandlerBuilder.this.log.isDebugEnabled()) {
				Socks5ProxyServerHandlerBuilder.this.log.debug(cause.toString());
				cause.printStackTrace();
			}
			ctx.close();
		}

		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().write(new ByteBufWrapTransferBuffer(ByteBuf.class.cast(msg)));
			} catch (Exception e) {
				// Release if exception
				ReferenceCountUtil.release(msg);
			}
		}
	}

	private class ContextCloseable implements Closeable {

		private final ChannelHandlerContext ctx;

		public ContextCloseable(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public void close() throws IOException {
			this.ctx.close();
		}
	}

	private class ByteBufWrapTransferBuffer implements TransferBuffer {

		private final ByteBuf buffer;

		public ByteBufWrapTransferBuffer(ByteBuf buffer) {
			super();
			this.buffer = buffer;
		}

		@Override
		public Object getBuffer() {
			return this.buffer;
		}

		@Override
		public TransferBuffer release() {
			if (this.buffer.refCnt() > 0) {
				ReferenceCountUtil.release(this.buffer);
			}
			return this;
		}
	}

	private class SocksCmdResponseWrap extends SocksResponse {

		private final Byte START_DOMAIN = 4;

		private BytestreamsProxy proxy;

		private SocksCmdResponse cmd;

		protected SocksCmdResponseWrap(BytestreamsProxy proxy, SocksCmdResponse cmd) {
			super(cmd.responseType());
			this.cmd = cmd;
			this.proxy = proxy;
		}

		@Override
		public void encodeAsByteBuf(ByteBuf buf) {
			try {
				this.cmd.encodeAsByteBuf(buf);
				byte[] proxy = this.proxy.getDomain().getBytes("UTF-8");
				buf.writerIndex(START_DOMAIN).writeByte(proxy.length).writeBytes(proxy).writeByte(0).writeByte(0);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
