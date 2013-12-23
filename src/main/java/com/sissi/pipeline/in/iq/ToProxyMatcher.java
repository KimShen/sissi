package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;

/**
 * @author kim 2013年12月18日
 */
public class ToProxyMatcher extends ClassMatcher {

	private BytestreamsProxy proxy;

	public ToProxyMatcher(Class<? extends Protocol> clazz, BytestreamsProxy proxy) {
		super(clazz);
		this.proxy = proxy;
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (this.proxy.getJid().equals(protocol.getParent().getTo()));
	}
}
