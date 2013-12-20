package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * @author kim 2013年12月18日
 */
public class BytestreamsSearchMatcher extends ClassMatcher {

	private final static String DEFAULT_HOST = "proxy.eu.jabber.org";

	private BytestreamsProxy proxy;

	public BytestreamsSearchMatcher(BytestreamsProxy proxy) {
		super(Bytestreams.class);
		this.proxy = proxy;
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (this.proxy.getJid().equals(protocol.getParent().getTo()) || protocol.getParent().getTo().equals(DEFAULT_HOST));
	}
}
