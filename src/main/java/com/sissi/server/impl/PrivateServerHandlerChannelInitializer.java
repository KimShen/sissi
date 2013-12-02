package com.sissi.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author kim 2013-11-19
 */
public class PrivateServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final PrivateServerHandlerBuilder serverHandlerBuilder;

	public PrivateServerHandlerChannelInitializer(PrivateServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.serverHandlerBuilder = serverHandlerBuilder;
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(this.serverHandlerBuilder.build());
	}
}
