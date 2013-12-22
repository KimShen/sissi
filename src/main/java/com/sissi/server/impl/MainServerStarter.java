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
import com.sissi.server.ServerStarter;
import com.sissi.server.exchange.impl.Sock5Server;

/**
 * @author kim 2013-11-19
 */
public class MainServerStarter implements ServerStarter {

	private final Log log = LogFactory.getLog(this.getClass());

	private final ServerBootstrap bootstrap = new ServerBootstrap();

	private final ChannelInitializer<SocketChannel> channelInitializer;

	private final ServerLoopGroup serverLoopGroup;

	private final Integer port;

	public MainServerStarter(ChannelInitializer<SocketChannel> channelInitializer, ServerLoopGroup serverLoopGroup, Integer port) {
		super();
		this.channelInitializer = channelInitializer;
		this.serverLoopGroup = serverLoopGroup;
		this.port = port;
	}

	@Override
	public MainServerStarter start() {
		try {
			bootstrap.group(serverLoopGroup.boss(), serverLoopGroup.event()).channel(NioServerSocketChannel.class).childHandler(this.channelInitializer);
			bootstrap.bind(this.port).addListener(new FailShutdownGenericFutureListener());
		} catch (Exception e) {
			this.log.fatal(e);
			this.closeAll();
		}
		return this;
	}

	@Override
	public MainServerStarter stop() {
		this.closeAll();
		return this;
	}

	private void closeAll() {
		this.serverLoopGroup.boss().shutdownGracefully();
		this.serverLoopGroup.event().shutdownGracefully();
	}

	private class FailShutdownGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				MainServerStarter.this.closeAll();
				future.cause().printStackTrace();
			}
		}
	}

	public static class MainServer {

		private final static String PREFIX = "classpath:";

		@SuppressWarnings("resource")
		public static void main(String[] args) throws Exception {
			Sock5Server.main(null);
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(PREFIX + "configs" + File.separatorChar + "config-loading.xml");
			ServerStarter start = context.getBean(ServerStarter.class);
			start.start();
		}
	}
}
