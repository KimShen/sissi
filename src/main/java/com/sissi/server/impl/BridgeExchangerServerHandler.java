package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import com.sissi.server.Exchanger;

/**
 * @author kim 2013年12月22日
 */
@Sharable
public class BridgeExchangerServerHandler extends ChannelInboundHandlerAdapter {

	public static final AttributeKey<Exchanger> EXCHANGER = AttributeKey.valueOf("exchanger");

	public void channelUnregistered(ChannelHandlerContext ctx) {
		ctx.attr(EXCHANGER).get().close();
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.attr(EXCHANGER).get().write(ByteBuf.class.cast(msg).nioBuffer());
	}
}