package com.sissi.pipeline.in.iq.muc.unique;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Unique;

/**
 * @author kim 2014年3月27日
 */
public class MucUniqueMatcher extends ClassMatcher {

	private String domain;

	public MucUniqueMatcher(String domain) {
		super(Unique.class);
		this.domain = domain;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.parent().type(ProtocolType.GET) && protocol.parent().to(this.domain);
	}
}
