package com.sissi.netty.listener;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author kim 2013-10-26
 */
public class FailShowdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

	private EventLoopGroup boss;

	private EventLoopGroup event;

	public FailShowdownGenericFutureListener(EventLoopGroup boss, EventLoopGroup event) {
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
