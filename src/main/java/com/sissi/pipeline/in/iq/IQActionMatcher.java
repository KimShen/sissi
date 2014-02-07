package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2013-11-4
 */
public class IQActionMatcher extends ClassMatcher {

	private final ProtocolType type;

	public IQActionMatcher(Class<? extends Protocol> clazz, String type) {
		super(clazz);
		this.type = ProtocolType.parse(type);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.getParent().type(this.type);
	}
}
