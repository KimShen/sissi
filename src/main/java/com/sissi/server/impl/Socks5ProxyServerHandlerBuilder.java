package com.sissi.server.impl;

import io.netty.channel.ChannelHandler;

import java.io.IOException;

import com.sissi.server.ExchangerContext;

/**
 * @author kim 2013年12月22日
 */
public class Socks5ProxyServerHandlerBuilder {

	private final ChannelHandler channelHandler;

	private final ExchangerContext exchangerContext;

	public Socks5ProxyServerHandlerBuilder(ChannelHandler channelHandler, ExchangerContext exchangerContext) {
		super();
		this.channelHandler = channelHandler;
		this.exchangerContext = exchangerContext;
	}

	public ChannelHandler build() throws IOException {
		return new Sock5ProxyServerHandler(channelHandler, exchangerContext);
	}
}
