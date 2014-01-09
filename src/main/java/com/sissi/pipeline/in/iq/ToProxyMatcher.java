package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月18日
 */
public class ToProxyMatcher extends ClassMatcher {

	private String jid;

	public ToProxyMatcher(Class<? extends Protocol> clazz, String jid) {
		super(clazz);
		this.jid = jid;
	}

	public String getJid() {
		return jid;
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (this.getJid().equals(protocol.getParent().getTo()));
	}
}
