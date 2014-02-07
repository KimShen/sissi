package com.sissi.server.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import com.sissi.server.ServerHandlerBuilder;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final ServerHandlerBuilder serverHandlerBuilder;

	private final int idleWrite;

	private final int idleRead;

	private final int idleAll;

	public Socks5ProxyServerHandlerChannelInitializer(int idleWrite, int idleRead, int idleAll, ServerHandlerBuilder serverHandlerBuilder) {
		super();
		this.serverHandlerBuilder = serverHandlerBuilder;
		this.idleWrite = idleWrite;
		this.idleRead = idleRead;
		this.idleAll = idleAll;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new SocksInitRequestDecoder()).addLast(new SocksCmdRequestDecoder()).addLast(new IdleStateHandler(this.idleRead, this.idleWrite, this.idleAll)).addLast(this.serverHandlerBuilder.build());
	}
}
