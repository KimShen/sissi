package com.sissi.netty.impl;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import com.sissi.netty.ServerLoopGroup;

/**
 * @author kim 2013-11-19
 */
public class MyServerLoopGroup implements ServerLoopGroup {
	
	@Override
	public EventLoopGroup boss() {
		return new NioEventLoopGroup();
	}

	@Override
	public EventLoopGroup event() {
		return new NioEventLoopGroup();
	}

}
