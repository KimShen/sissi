package com.sissi.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final Socks5ProxyServerHandlerBuilder serverHandlerBuilder;

	private final Integer idleRead;

	private final Integer idleWrite;

	private final Integer idleAll;

	public Socks5ProxyServerHandlerChannelInitializer(Integer idleRead, Integer idleWrite, Integer idleAll, Socks5ProxyServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.serverHandlerBuilder = serverHandlerBuilder;
		this.idleRead = idleRead;
		this.idleWrite = idleWrite;
		this.idleAll = idleAll;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new SocksInitRequestDecoder());
		ch.pipeline().addLast(new SocksCmdRequestDecoder());
		ch.pipeline().addLast(new IdleStateHandler(this.idleRead, this.idleWrite, this.idleAll));
		ch.pipeline().addLast(this.serverHandlerBuilder.build());
	}
}
