package com.sissi.server.exchange.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.codec.socks.SocksMessageEncoder;

public class Sock5Server {

	private final int port;

	public Sock5Server(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			final ServerBootstrap sb = new ServerBootstrap();
			final Sock5ServerHandler ssh = new Sock5ServerHandler();
			sb.group(bossGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(final SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new SocksInitRequestDecoder());
					pipeline.addLast(new SocksCmdRequestDecoder());
					pipeline.addLast(new SocksMessageEncoder());
					ch.pipeline().addLast(new SocksCmdRequestDecoder(), ssh);
				}
			});
			sb.bind(port);
		} finally {
			// bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new Sock5Server(8888).run();
	}

}
