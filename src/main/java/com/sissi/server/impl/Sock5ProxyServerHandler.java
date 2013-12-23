package com.sissi.server.impl;

import io.netty.channel.ChannelHandler;
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
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class Sock5ProxyServerHandler extends ChannelInboundHandlerAdapter {

	private final ChannelHandler channelHandler;

	private final ExchangerContext exchangerContext;

	public Sock5ProxyServerHandler(ChannelHandler channelHandler, ExchangerContext exchangerContext) {
		super();
		this.channelHandler = channelHandler;
		this.exchangerContext = exchangerContext;
	}

	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg.getClass() == SocksInitRequest.class) {
			ctx.writeAndFlush(this.buildInit());
		} else if (msg.getClass() == SocksCmdRequest.class) {
			SocksCmdRequest cmd = SocksCmdRequest.class.cast(msg);
			if (this.exchangerContext.contains(cmd.host())) {
				ctx.writeAndFlush(this.buildCmd()).addListener(new BridgeExchangeListener(ctx, this.exchangerContext.get(cmd.host())));
			} else {
				this.exchangerContext.set(cmd.host(), new BridgeExchanger(ctx));
				ctx.writeAndFlush(this.buildCmd());
			}
		}
	}

	private SocksResponse buildInit() {
		return new SocksInitResponse(SocksAuthScheme.NO_AUTH);
	}

	private SocksResponse buildCmd() {
		return new SocksCmdResponse(SocksCmdStatus.SUCCESS, SocksAddressType.IPv4);
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
				ctx.pipeline().addFirst(Sock5ProxyServerHandler.this.channelHandler);
				ctx.pipeline().context(BridgeExchangerServerHandler.class).attr(BridgeExchangerServerHandler.EXCHANGER).set(exchanger);
			}
		}
	}
}
