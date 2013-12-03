package com.sissi.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sissi.server.ServerLoopGroup;
import com.sissi.server.ServerStart;

/**
 * @author kim 2013-11-19
 */
public class MainServerStart implements ServerStart {

	private final Log log = LogFactory.getLog(this.getClass());

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	private final ChannelInitializer<SocketChannel> channelInitializer;

	private final ServerLoopGroup serverLoopGroup;

	private final Integer port;

	public MainServerStart(ChannelInitializer<SocketChannel> channelInitializer, ServerLoopGroup serverLoopGroup, Integer port) {
		super();
		this.channelInitializer = channelInitializer;
		this.serverLoopGroup = serverLoopGroup;
		this.port = port;
	}

	@Override
	public void start() {
		try {
			bootstrap.group(serverLoopGroup.boss(), serverLoopGroup.event()).channel(NioServerSocketChannel.class).childHandler(this.channelInitializer);
			bootstrap.bind(this.port).addListener(new FailShutdownGenericFutureListener());
		} catch (Exception e) {
			this.log.fatal(e);
			this.closeAll();
		}
	}

	@Override
	public void stop() {
		this.closeAll();
	}

	private void closeAll() {
		this.serverLoopGroup.boss().shutdownGracefully();
		this.serverLoopGroup.event().shutdownGracefully();
	}

	private class FailShutdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				MainServerStart.this.closeAll();
				future.cause().printStackTrace();
			}
		}
	}

	public static class MainServer {

		private final static String PREFIX = "classpath:";

		@SuppressWarnings("resource")
		public static void main(String[] args) throws Exception {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(PREFIX + "configs" + File.separatorChar + "config-loading.xml");
			ServerStart start = context.getBean(ServerStart.class);
			start.start();
		}
	}
}
