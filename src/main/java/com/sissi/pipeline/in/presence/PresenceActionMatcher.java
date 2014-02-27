package com.sissi.pipeline.in.presence;

import java.util.HashSet;
import java.util.Set;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2013-11-4
 */
public class PresenceActionMatcher extends ClassMatcher {

	private final PresenceType[] types;

	private final boolean directed;

	public PresenceActionMatcher(boolean directed, Set<String> types) {
		super(Presence.class);
		Set<PresenceType> pts = new HashSet<PresenceType>();
		for (String type : types) {
			pts.add(PresenceType.parse(type));
		}
		this.types = pts.toArray(new PresenceType[] {});
		this.directed = directed;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && (!protocol.to() || this.directed) && PresenceType.parse(protocol.getType()).in(this.types);
	}
}
