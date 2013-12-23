package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月23日
 */
public class ToServerMatcher extends ClassMatcher {

	private String host;

	public ToServerMatcher(Class<? extends Protocol> clazz, String host) {
		super(clazz);
		this.host = host;
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (protocol.getParent().getTo() == null || protocol.getParent().getTo().equals(this.host));
	}
}
