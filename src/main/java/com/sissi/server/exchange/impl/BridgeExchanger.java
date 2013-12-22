package com.sissi.server.exchange.impl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.sissi.server.exchange.Exchanger;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchanger implements Exchanger {

	private final String host;

	private final ChannelHandlerContext context;

	public BridgeExchanger(String host, ChannelHandlerContext context) throws IOException {
		this.host = host;
		this.context = context;
	}

	@Override
	public Exchanger write(ByteBuffer bytes) {
		this.context.writeAndFlush(Unpooled.wrappedBuffer(bytes));
		return this;
	}

	@Override
	public String getHost() {
		return this.host;
	}
}
