package com.sissi.server;

import io.netty.channel.EventLoopGroup;

/**
 * @author kim 2013-11-19
 */
public interface ServerLoopGroup {

	public EventLoopGroup boss();
	
	public EventLoopGroup event();
}
