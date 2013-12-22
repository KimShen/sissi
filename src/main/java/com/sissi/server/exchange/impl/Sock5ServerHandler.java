package com.sissi.server.exchange.impl;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.socks.SocksAddressType;
import io.netty.handler.codec.socks.SocksAuthScheme;
import io.netty.handler.codec.socks.SocksCmdRequest;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksCmdResponse;
import io.netty.handler.codec.socks.SocksCmdStatus;
import io.netty.handler.codec.socks.SocksInitRequest;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.codec.socks.SocksInitResponse;
import io.netty.handler.codec.socks.SocksMessageEncoder;
import io.netty.handler.codec.socks.SocksResponse;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2013年12月22日
 */
@Sharable
public class Sock5ServerHandler extends ChannelInboundHandlerAdapter {

	private Map<String, BridgeExchanger> hosts = new HashMap<String, BridgeExchanger>();

	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg.getClass() == SocksInitRequest.class) {
			System.out.println("init");
			ctx.writeAndFlush(this.buildInit());
		}
		if (msg.getClass() == SocksCmdRequest.class) {
			System.out.println("cmd");
			SocksCmdRequest cmd = SocksCmdRequest.class.cast(msg);
			System.out.println(cmd.host());
			if (this.hosts.containsKey(cmd.host())) {
				final BridgeExchanger exchager = this.hosts.get(cmd.host());
//				ctx.attr(BridgeExchangeServerHandler.exchanger).set(exchager);
				System.out.println(ctx.attr(BridgeExchangeServerHandler.exchanger).get() + "," + ctx);
				ctx.writeAndFlush(this.buildCmd()).addListener(new GenericFutureListener<Future<Void>>() {
					public void operationComplete(Future<Void> future) throws Exception {
						if (future.isSuccess()) {
							System.out.println("Ready2");
							ctx.pipeline().addFirst(new BridgeExchangeServerHandler());
							ctx.pipeline().context(BridgeExchangeServerHandler.class).attr(BridgeExchangeServerHandler.exchanger).set(exchager);
						}
					}
				});
			} else {
				BridgeExchanger exchager = new BridgeExchanger(cmd.host(), ctx);
				this.hosts.put(cmd.host(), exchager);
				ctx.attr(BridgeExchangeServerHandler.exchanger).set(exchager);
				ctx.writeAndFlush(this.buildCmd()).addListener(new GenericFutureListener<Future<Void>>() {
					public void operationComplete(Future<Void> future) throws Exception {
						if (future.isSuccess()) {
							System.out.println("Ready1");
						}
					}
				});
			}
		}
	}

	private SocksResponse buildInit() {
		return new SocksInitResponse(SocksAuthScheme.NO_AUTH);
	}

	private SocksResponse buildCmd() {
		return new SocksCmdResponse(SocksCmdStatus.SUCCESS, SocksAddressType.IPv4);
	}
}
