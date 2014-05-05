package com.sissi.server.netty.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.server.Server;
import com.sissi.server.netty.ServerLoopGroup;
import com.sissi.server.status.ServerStatus;

/**
 * @author kim 2013-11-19
 */
public class NioServer implements Server {

	private final Log log = LogFactory.getLog(this.getClass());

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	private final ChannelInitializer<SocketChannel> channelInitializer;

	private final ServerLoopGroup serverLoopGroup;

	private final ServerStatus serverStatus;

	private final int port;

	public NioServer(ChannelInitializer<SocketChannel> channelInitializer, ServerLoopGroup serverLoopGroup, ServerStatus serverStatus, int port) {
		super();
		this.channelInitializer = channelInitializer;
		this.serverLoopGroup = serverLoopGroup;
		this.serverStatus = serverStatus;
		this.port = port;
	}

	@Override
	public NioServer start() {
		try {
			this.bootstrap.group(this.serverLoopGroup.boss(), this.serverLoopGroup.event()).channel(NioServerSocketChannel.class).childHandler(this.channelInitializer);
			this.bootstrap.bind(this.port).addListener(new FailShutdownGenericFutureListener());
			this.serverStatus.update(ServerStatus.STATUS_STARTED, String.valueOf(System.currentTimeMillis()));
		} catch (Exception e) {
			this.log.fatal(e.toString());
			Trace.trace(this.log, e);
			this.closeAll();
		}
		return this;
	}

	@Override
	public NioServer stop() {
		return this.closeAll();
	}

	private NioServer closeAll() {
		this.serverLoopGroup.boss().shutdownGracefully();
		this.serverLoopGroup.event().shutdownGracefully();
		return this;
	}

	private class FailShutdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				NioServer.this.closeAll();
				NioServer.this.log.fatal(future.cause());
				Trace.trace(NioServer.this.log, future.cause());
			}
		}
	}
}
