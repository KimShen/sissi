package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013-11-4
 */
public class IQActionMatcher extends MatchClass {

	private final Type type;

	public IQActionMatcher(Class<? extends Protocol> clazz, String type) {
		super(clazz);
		this.type = Type.parse(type);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getParent().getType());
	}
}
