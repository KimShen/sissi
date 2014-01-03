package com.sissi.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author kim 2013-11-19
 */
public class PrivateServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final Integer idleRead;

	private final Integer idleWrite;

	private final Integer idleAll;

	private final PrivateServerHandlerBuilder serverHandlerBuilder;

	public PrivateServerHandlerChannelInitializer(Integer idleRead, Integer idleWrite, Integer idleAll, PrivateServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.idleRead = idleRead;
		this.idleWrite = idleWrite;
		this.idleAll = idleAll;
		this.serverHandlerBuilder = serverHandlerBuilder;
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new IdleStateHandler(this.idleRead, this.idleWrite, this.idleAll));
		ch.pipeline().addLast(this.serverHandlerBuilder.build());
	}
}
