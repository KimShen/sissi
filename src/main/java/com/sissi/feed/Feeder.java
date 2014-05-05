package com.sissi.feed;

import com.sissi.protocol.Protocol;

/**
 * XMPP节推送器
 * 
 * @author kim 2013-10-23
 */
public interface Feeder {

	public Feeder feed(Protocol protocol);
}
