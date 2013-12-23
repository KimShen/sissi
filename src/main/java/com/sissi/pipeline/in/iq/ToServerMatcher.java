package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;

/**
 * @author kim 2013年12月23日
 */
public class ToServerMatcher extends ClassMatcher {

	private String host;

	public ToServerMatcher(String host) {
		super(Bytestreams.class);
		this.host = host;
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (protocol.getTo() == null || protocol.getTo().equals(this.host));
	}
}
