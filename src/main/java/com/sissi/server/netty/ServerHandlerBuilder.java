package com.sissi.server.netty;

import io.netty.channel.ChannelHandler;

import java.io.IOException;

/**
 * @author kim 2014年2月5日
 */
public interface ServerHandlerBuilder {

	public ChannelHandler build() throws IOException;
}
