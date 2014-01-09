package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013-11-4
 */
public class IQActionMatcher extends ClassMatcher {

	private final Type type;

	private final Boolean includeNull;

	public IQActionMatcher(Class<? extends Protocol> clazz, String type) {
		super(clazz);
		this.type = Type.parse(type);
		this.includeNull = false;
	}

	public IQActionMatcher(Class<? extends Protocol> clazz, String type, Boolean includeNull) {
		super(clazz);
		this.type = Type.parse(type);
		this.includeNull = includeNull;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getParent().getType()) || (this.includeNull ? false : protocol.getParent().getType() == null);
	}
}
