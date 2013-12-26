package com.sissi.server.impl;

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
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerBuilder {

	private final ChannelHandler channelHandler = new BridgeExchangerServerHandler();

	private final Log log = LogFactory.getLog(this.getClass());

	private final ExchangerContext exchangerContext;

	private final AttributeKey<Exchanger> exchanger;

	private final byte[] init;

	private final byte[] cmd;

	public Socks5ProxyServerHandlerBuilder(BytestreamsProxy proxy, ExchangerContext exchangerContext, String exchanger) {
		super();
		this.exchangerContext = exchangerContext;
		this.exchanger = AttributeKey.valueOf(exchanger);
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

		public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().closeIniter();
		}

		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			ctx.close();
			if (Socks5ProxyServerHandlerBuilder.this.log.isDebugEnabled()) {
				Socks5ProxyServerHandlerBuilder.this.log.debug(cause.toString());
			}
		}

		public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
			this.prepareCmd(ctx, msg, ctx.write(this.build(msg))).flush();
		}

		private ChannelHandlerContext prepareCmd(final ChannelHandlerContext ctx, Object msg, ChannelFuture future) throws IOException {
			if (msg.getClass() == SocksCmdRequest.class) {
				SocksCmdRequest cmd = SocksCmdRequest.class.cast(msg);
				return Socks5ProxyServerHandlerBuilder.this.exchangerContext.isTarget(cmd.host()) ? this.add(cmd, ctx) : this.add(Socks5ProxyServerHandlerBuilder.this.exchangerContext.get(cmd.host()), future, ctx);
			}
			return ctx;
		}

		private ChannelHandlerContext add(SocksCmdRequest cmd, ChannelHandlerContext ctx) throws IOException {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).set(Socks5ProxyServerHandlerBuilder.this.exchangerContext.set(cmd.host(), new NetworkTransfer(ctx)));
			return ctx;
		}

		private ChannelHandlerContext add(Exchanger exchanger, ChannelFuture future, ChannelHandlerContext ctx) throws IOException {
			future.addListener(new BridgeExchangeListener(ctx, exchanger.initer(new ContextCloseable(ctx))));
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
				this.exchanger = exchanger;
			}

			@Override
			public void operationComplete(Future<Void> future) throws Exception {
				if (future.isSuccess()) {
					ctx.flush();
					ctx.pipeline().addFirst(Socks5ProxyServerHandlerBuilder.this.channelHandler);
					ctx.pipeline().context(BridgeExchangerServerHandler.class).attr(Socks5ProxyServerHandlerBuilder.this.exchanger).set(exchanger);
				}
			}
		}
	}

	@Sharable
	private class BridgeExchangerServerHandler extends ChannelInboundHandlerAdapter {

		public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().closeTarget();
		}
		
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			ctx.close();
			if (Socks5ProxyServerHandlerBuilder.this.log.isDebugEnabled()) {
				Socks5ProxyServerHandlerBuilder.this.log.debug(cause.toString());
			}
		}

		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().write(ByteBuf.class.cast(msg).nioBuffer());
		}
	}

	private class ContextCloseable implements Closeable {

		private ChannelHandlerContext ctx;

		public ContextCloseable(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public void close() throws IOException {
			this.ctx.close();
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
				byte[] proxy = this.proxy.getHost().getBytes("UTF-8");
				buf.writerIndex(START_DOMAIN).writeByte(proxy.length).writeBytes(proxy).writeByte(0).writeByte(0);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
