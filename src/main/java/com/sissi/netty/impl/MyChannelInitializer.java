package com.sissi.netty.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author kim 2013-11-19
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

	private MyServerHandlerBuilder serverHandlerBuilder;

	public MyChannelInitializer(MyServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.serverHandlerBuilder = serverHandlerBuilder;
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(this.serverHandlerBuilder.builder());
	}
}
