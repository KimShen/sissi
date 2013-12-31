package com.sissi.pipeline.in.presence.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.Presence.Type;

/**
 * @author kim 2013年12月31日
 */
public class PresenceMucActionMatcher extends ClassMatcher {

	private final Type type;

	public PresenceMucActionMatcher(String type) {
		super(Presence.class);
		this.type = Type.parse(type);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.type.equals(protocol.getType());
	}
}
