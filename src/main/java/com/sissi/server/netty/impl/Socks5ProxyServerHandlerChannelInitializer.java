package com.sissi.server.netty.impl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socks.SocksCmdRequestDecoder;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import com.sissi.server.netty.ChannelHandlerBuilder;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final int idleWrite;

	private final int idleRead;

	private final int idleAll;

	private final ChannelHandlerBuilder channelHandlerBuilder;

	public Socks5ProxyServerHandlerChannelInitializer(int idleWrite, int idleRead, int idleAll, ChannelHandlerBuilder channelHandlerBuilder) {
		super();
		this.idleAll = idleAll;
		this.idleRead = idleRead;
		this.idleWrite = idleWrite;
		this.channelHandlerBuilder = channelHandlerBuilder;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new SocksInitRequestDecoder()).addLast(new SocksCmdRequestDecoder()).addLast(new IdleStateHandler(this.idleRead, this.idleWrite, this.idleAll)).addLast(this.channelHandlerBuilder.build());
	}
}
