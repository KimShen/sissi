package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
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

import java.io.IOException;

import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerBuilder {

	private final ChannelHandler channelHandler = new BridgeExchangerServerHandler();

	private final ExchangerContext exchangerContext;

	private final AttributeKey<Exchanger> exchanger;

	public Socks5ProxyServerHandlerBuilder(ExchangerContext exchangerContext, String exchanger) {
		super();
		this.exchangerContext = exchangerContext;
		this.exchanger = AttributeKey.valueOf(exchanger);
	}

	public ChannelHandler build() throws IOException {
		return new Sock5ProxyServerHandler();
	}

	private class Sock5ProxyServerHandler extends ChannelInboundHandlerAdapter {

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
			Socks5ProxyServerHandlerBuilder.this.exchangerContext.set(cmd.host(), new BridgeExchanger(ctx));
			return ctx;
		}

		private ChannelHandlerContext add(Exchanger exchanger, ChannelFuture future, ChannelHandlerContext ctx) throws IOException {
			future.addListener(new BridgeExchangeListener(ctx, exchanger));
			return ctx;
		}

		private SocksResponse build(Object msg) {
			return msg.getClass() == SocksInitRequest.class ? new SocksInitResponse(SocksAuthScheme.NO_AUTH) : new SocksCmdResponse(SocksCmdStatus.SUCCESS, SocksAddressType.DOMAIN);
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
					ctx.pipeline().addFirst(Socks5ProxyServerHandlerBuilder.this.channelHandler);
					ctx.pipeline().context(BridgeExchangerServerHandler.class).attr(Socks5ProxyServerHandlerBuilder.this.exchanger).set(exchanger);
				}
			}
		}
	}

	@Sharable
	private class BridgeExchangerServerHandler extends ChannelInboundHandlerAdapter {

		public void channelUnregistered(ChannelHandlerContext ctx) {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().close();
		}

		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ctx.attr(Socks5ProxyServerHandlerBuilder.this.exchanger).get().write(ByteBuf.class.cast(msg).nioBuffer());
		}
	}
}
