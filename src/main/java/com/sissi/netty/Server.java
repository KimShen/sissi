package com.sissi.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sissi.netty.listener.FailShowdownGenericFutureListener;

public class Server {

	public void run() throws Exception {
		InputStream configs = null;
		try {
			configs = Thread.currentThread().getContextClassLoader().getResourceAsStream("loading.properties");
			final ApplicationContext context = new ClassPathXmlApplicationContext(IOUtils.readLines(configs).toArray(new String[]{}));
			final ServerHandlerBuilder serverHandlerBuilder = context.getBean("serverHandlerBuilder", ServerHandlerBuilder.class);
			final EventLoopGroup bossGroup = new NioEventLoopGroup();
			final EventLoopGroup eventGroup = new NioEventLoopGroup();
			ServerBootstrap c = new ServerBootstrap();
			c.group(bossGroup, eventGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(serverHandlerBuilder.builder());
				}
			});
			c.bind(8080).addListener(new FailShowdownGenericFutureListener(bossGroup, eventGroup));

		} finally {
			IOUtils.closeQuietly(configs);
		}
	}

	public static void main(String[] args) throws Exception {
		new Server().run();
	}
}