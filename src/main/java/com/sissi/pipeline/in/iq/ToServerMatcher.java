package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月23日
 */
public class ToServerMatcher extends ToProxyMatcher {

	public ToServerMatcher(Class<? extends Protocol> clazz, String jid) {
		super(clazz, jid);
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) || protocol.getParent().getTo() == null;
	}
}
