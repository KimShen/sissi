package com.sissi.context;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-8
 */
public interface Writeable {

	public void writeAndFlush(Protocol protocol);
}