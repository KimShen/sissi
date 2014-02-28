package com.sissi.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.server.ServerStarter;
import com.sissi.server.ServerStatus;
import com.sissi.server.netty.ServerLoopGroup;

/**
 * @author kim 2013-11-19
 */
public class NioServerStarter implements ServerStarter {

	private final Log log = LogFactory.getLog(this.getClass());

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	private final ChannelInitializer<SocketChannel> channelInitializer;

	private final ServerLoopGroup serverLoopGroup;

	private final ServerStatus serverStatus;

	private final Integer port;

	public NioServerStarter(ChannelInitializer<SocketChannel> channelInitializer, ServerLoopGroup serverLoopGroup, ServerStatus serverStatus, Integer port) {
		super();
		this.channelInitializer = channelInitializer;
		this.serverLoopGroup = serverLoopGroup;
		this.serverStatus = serverStatus;
		this.port = port;
	}

	@Override
	public NioServerStarter start() {
		try {
			bootstrap.group(serverLoopGroup.boss(), serverLoopGroup.event()).channel(NioServerSocketChannel.class).childHandler(this.channelInitializer);
			bootstrap.bind(this.port).addListener(new FailShutdownGenericFutureListener());
			this.serverStatus.offer(ServerStatus.STATUS_STARTED, String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			this.log.fatal(e.toString());
			Trace.trace(this.log, e);
			this.closeAll();
		}
		return this;
	}

	@Override
	public NioServerStarter stop() {
		return this.closeAll();
	}

	private NioServerStarter closeAll() {
		this.serverLoopGroup.boss().shutdownGracefully();
		this.serverLoopGroup.event().shutdownGracefully();
		return this;
	}

	private class FailShutdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				NioServerStarter.this.closeAll();
				Trace.trace(NioServerStarter.this.log, future.cause());
			}
		}
	}
}
