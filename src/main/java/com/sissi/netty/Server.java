package com.sissi.netty;

<<<<<<< HEAD
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kim 2013-11-19
 */
public class Server {

	public void run() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(this.reading().toArray(new String[] {}));
		context.getBean(ServerStart.class).start();
	}

	private List<String> reading() {
		List<String> files = new ArrayList<String>();
		for (String file : new File(Thread.currentThread().getContextClassLoader().getResource("config").getFile()).list()) {
			files.add("classpath:config/" + file);
		}
		return files;
=======
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
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}

	public static void main(String[] args) throws Exception {
		new Server().run();
	}
}