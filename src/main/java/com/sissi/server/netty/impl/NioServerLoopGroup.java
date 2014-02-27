package com.sissi.server.netty.impl;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import com.sissi.server.netty.ServerLoopGroup;

/**
 * @author kim 2013-11-19
 */
public class NioServerLoopGroup implements ServerLoopGroup {
	
	@Override
	public EventLoopGroup boss() {
		return new NioEventLoopGroup();
	}

	@Override
	public EventLoopGroup event() {
		return new NioEventLoopGroup();
	}
}
