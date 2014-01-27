package com.sissi.pipeline.in.presence.status;

import java.util.HashSet;
import java.util.Set;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-4
 */
public class PresenceActionMatcher extends ClassMatcher {

	private final Set<String> types;

	public PresenceActionMatcher() {
		this((String) null);
	}

	public PresenceActionMatcher(String... types) {
		super(Presence.class);
		this.types = new HashSet<String>();
		for (String type : types) {
			this.types.add(type != null ? type.toLowerCase() : null);
		}
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && this.types.contains(protocol.getType());
	}
}
