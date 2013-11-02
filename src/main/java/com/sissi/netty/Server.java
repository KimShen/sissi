package com.sissi.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sissi.context.JID;
import com.sissi.group.impl.GroupBroadcast;

public class Server {

	public void run() throws Exception {
		String[] configs = new String[] { "classpath:config/config-thread.xml", "classpath:config/config-addressing.xml", "classpath:config/config-feed.xml", "classpath:config/config-netty.xml", "classpath:config/config-processor.xml", "classpath:config/config-reader.xml", "classpath:config/config-writer.xml" };
		final ApplicationContext context = new ClassPathXmlApplicationContext(configs);
		final ServerHandlerBuilder serverHandlerBuilder = context.getBean("serverHandlerBuilder", ServerHandlerBuilder.class);
		final GroupBroadcast group = new GroupBroadcast(context.getBean("systemJID", JID.class));
		BroadCastRunnable runnable = new BroadCastRunnable(group);
		final EventLoopGroup bossGroup = new NioEventLoopGroup();
		final EventLoopGroup eventGroup = new NioEventLoopGroup();
		ServerBootstrap c = new ServerBootstrap();
		c.group(bossGroup, eventGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(serverHandlerBuilder.builder(group));
			}
		});
		c.bind(8080);
	}

	public static void main(String[] args) throws Exception {
		new Server().run();
	}

	private static class BroadCastRunnable implements Runnable {

		private GroupBroadcast broadcast;

		public BroadCastRunnable(GroupBroadcast broadcast) {
			super();
			this.broadcast = broadcast;
			new Thread(this).start();
		}

		@Override
		public void run() {
			try {
				while (true) {
					BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
					String s = null;
					while ((s = bfReader.readLine()) != null) {
						System.out.println(">>");
						this.broadcast.broadcast(s, broadcast.jid(), null);
					}
					bfReader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}