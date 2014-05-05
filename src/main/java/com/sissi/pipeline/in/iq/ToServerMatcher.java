package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Protocol;

/**
 * 匹配To为服务器域或未指定
 * 
 * @author kim 2013年12月23日
 */
public class ToServerMatcher extends ToProxyMatcher {

	public ToServerMatcher(Class<? extends Protocol> clazz, String jid) {
		super(clazz, jid);
	}

	/*
	 * 匹配Presence.to为服务器域或不存在
	 * 
	 * @see com.sissi.pipeline.in.iq.ToProxyMatcher#match(com.sissi.protocol.Protocol)
	 */
	public boolean match(Protocol protocol) {
		return super.match(protocol) || !protocol.parent().to();
	}
}
