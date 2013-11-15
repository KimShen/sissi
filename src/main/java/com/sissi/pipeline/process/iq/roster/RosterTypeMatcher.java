package com.sissi.pipeline.process.iq.roster;

import com.sissi.pipeline.process.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.roster.Roster;

/**
 * @author kim 2013-11-4
 */
public class RosterTypeMatcher extends ClassMatcher {
	
	private Type type;
	
	public RosterTypeMatcher(String type) {
		super(Roster.class);
		this.type = Type.parse(type);
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.isSetIQ(protocol);
	}

	private boolean isSetIQ(Protocol protocol) {
		return protocol.hasParent() && type.toString().equals(protocol.getParent().getType());
	}
}
