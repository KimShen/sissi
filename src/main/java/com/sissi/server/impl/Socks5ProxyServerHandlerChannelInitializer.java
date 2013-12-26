package com.sissi.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {


	private final Socks5ProxyServerHandlerBuilder serverHandlerBuilder;

	public Socks5ProxyServerHandlerChannelInitializer(Socks5ProxyServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.serverHandlerBuilder = serverHandlerBuilder;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new SocksInitRequestDecoder());
		ch.pipeline().addLast(new SocksCmdRequestDecoder());
		ch.pipeline().addLast(this.serverHandlerBuilder.build());
	}
}
