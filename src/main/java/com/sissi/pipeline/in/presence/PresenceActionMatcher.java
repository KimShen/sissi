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

	private final Set<PresenceType> types = new HashSet<PresenceType>();

	private final boolean directed;

	public PresenceActionMatcher(Set<String> types) {
		this(false, types);
	}

	public PresenceActionMatcher(boolean directed, Set<String> types) {
		super(Presence.class);
		this.directed = directed;
		for (String type : types) {
			this.types.add(PresenceType.parse(type));
		}
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && (!protocol.to() || this.directed) && this.types.contains(PresenceType.parse(protocol.getType()));
	}
}
