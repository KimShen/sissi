package com.sissi.pipeline.in.iq.last;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.last.Last;

/**
 * @author kim 2014年2月10日
 */
public class LastDomainMatcher extends ClassMatcher {

	private final String domain;

	private final boolean server;

	public LastDomainMatcher(String domain, boolean server) {
		super(Last.class);
		this.domain = domain;
		this.server = server;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && ( protocol.to(this.domain) == this.server);
	}
}
