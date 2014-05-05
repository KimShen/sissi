package com.sissi.pipeline.in.iq.last;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.last.Last;

/**
 * <iq to='服务器域或JID' type='get'><query xmlns='jabber:iq:last'/></iq>
 * 
 * @author kim 2014年2月10日
 */
public class LastMatcher extends ClassMatcher {

	private final String domain;

	private final boolean server;

	/**
	 * @param domain
	 * @param server 是否为服务器域
	 */
	public LastMatcher(String domain, boolean server) {
		super(Last.class);
		this.domain = domain;
		this.server = server;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && (protocol.parent().to(this.domain) == this.server);
	}
}
