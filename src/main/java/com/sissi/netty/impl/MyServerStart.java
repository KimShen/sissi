package com.sissi.netty.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.sissi.netty.ServerLoopGroup;
import com.sissi.netty.ServerStart;

/**
 * @author kim 2013-11-19
 */
public class MyServerStart implements ServerStart {

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	private ChannelInitializer<SocketChannel> channelInitializer;

	private ServerLoopGroup serverLoopGroup;

	private Integer port;

	public MyServerStart(ChannelInitializer<SocketChannel> channelInitializer, ServerLoopGroup serverLoopGroup, Integer port) {
		super();
		this.channelInitializer = channelInitializer;
		this.serverLoopGroup = serverLoopGroup;
		this.port = port;
	}

	@Override
	public void start() {
		bootstrap.group(serverLoopGroup.boss(), serverLoopGroup.event()).channel(NioServerSocketChannel.class).childHandler(this.channelInitializer);
		bootstrap.bind(this.port).addListener(new FailShutdownGenericFutureListener(serverLoopGroup.boss(), serverLoopGroup.event()));
	}

	@Override
	public void stop() {
		this.serverLoopGroup.boss().shutdownGracefully();
		this.serverLoopGroup.event().shutdownGracefully();
	}

	private class FailShutdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private EventLoopGroup boss;

		private EventLoopGroup event;

		public FailShutdownGenericFutureListener(EventLoopGroup boss, EventLoopGroup event) {
			super();
			this.boss = boss;
			this.event = event;
		}

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				this.boss.shutdownGracefully();
				this.event.shutdownGracefully();
			}
		}
	}
}
