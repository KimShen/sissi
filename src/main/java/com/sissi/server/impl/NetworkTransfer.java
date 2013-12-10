package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final ChannelHandlerContext context;

	public NetworkTransfer(ChannelHandlerContext context) {
		super();
		this.context = context;
	}

	public ByteBuf allocBuffer() {
		return this.context.alloc().buffer();
	}

	@Override
	public Transfer transfer(ByteBuf bytebuf) {
		this.context.writeAndFlush(bytebuf).addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}

	@Override
	public Transfer close() {
		this.context.close().addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}
}
