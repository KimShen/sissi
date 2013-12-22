package com.sissi.server.exchange.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangeServerHandler extends ChannelInboundHandlerAdapter {
	
	public static final AttributeKey<BridgeExchanger> exchanger = AttributeKey.valueOf("exchanger");

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(ctx + "," + ctx.attr(BridgeExchangeServerHandler.exchanger).get());
		ctx.attr(exchanger).get().write(ByteBuf.class.cast(msg).nioBuffer());
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}