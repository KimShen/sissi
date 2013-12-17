package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final GenericFutureListener<Future<Void>> futureListener;

	private final ChannelHandlerContext context;

	public NetworkTransfer(GenericFutureListener<Future<Void>> futureListener, ChannelHandlerContext context) {
		super();
		this.futureListener = futureListener;
		this.context = context;
	}

	public ByteBuf allocBuffer() {
		return this.context.alloc().buffer();
	}

	@Override
	public Transfer transfer(ByteBuf bytebuf) {
		this.context.writeAndFlush(bytebuf).addListener(this.futureListener).addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}

	@Override
	public Transfer close() {
		this.context.close().addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}
}
