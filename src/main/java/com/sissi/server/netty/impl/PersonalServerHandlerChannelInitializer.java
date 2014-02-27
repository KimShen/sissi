package com.sissi.server.netty.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import com.sissi.server.netty.ServerHandlerBuilder;

/**
 * @author kim 2013-11-19
 */
public class PersonalServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final int idleWrite;

	private final int idleRead;

	private final int idleAll;

	private final ServerHandlerBuilder serverHandlerBuilder;

	public PersonalServerHandlerChannelInitializer(int idleWrite, int idleRead, int idleAll, ServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.idleAll = idleAll;
		this.idleRead = idleRead;
		this.idleWrite = idleWrite;
		this.serverHandlerBuilder = serverHandlerBuilder;
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new IdleStateHandler(this.idleRead, this.idleWrite, this.idleAll)).addLast(this.serverHandlerBuilder.build());
	}
}
