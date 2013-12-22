package com.sissi.server.impl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.sissi.server.Exchanger;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchanger implements Exchanger {

	private final ChannelHandlerContext context;

	public BridgeExchanger(ChannelHandlerContext context) throws IOException {
		this.context = context;
	}

	@Override
	public Exchanger write(ByteBuffer bytes) {
		this.context.writeAndFlush(Unpooled.wrappedBuffer(bytes));
		return this;
	}

	@Override
	public Exchanger close() {
		this.context.close();
		return this;
	}
}
