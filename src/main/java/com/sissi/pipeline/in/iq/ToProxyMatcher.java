package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;

/**
 * 匹配To为指定域或本地域(localhost)
 * 
 * @author kim 2013年12月18日
 */
public class ToProxyMatcher extends ClassMatcher {

	private final String localhostDomain = "localhost";

	private final String localhostIp = "127.0.0.1";

	private final String jid;

	public ToProxyMatcher(Class<? extends Protocol> clazz, String jid) {
		super(clazz);
		this.jid = jid;
	}

	/*
	 * 匹配Presence.to为指定域或本地域(localhost)
	 * 
	 * @see com.sissi.pipeline.in.ClassMatcher#match(com.sissi.protocol.Protocol)
	 */
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.parent().to(this.jid, this.localhostDomain, this.localhostIp);
	}
}
