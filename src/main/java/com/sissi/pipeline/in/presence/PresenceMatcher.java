package com.sissi.pipeline.in.presence;

import java.util.HashSet;
import java.util.Set;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * <presence type=Xxx to=Xxx/></p>
 * 
 * @author kim 2013-11-4
 */
public class PresenceMatcher extends ClassMatcher {

	private final PresenceType[] types;

	private final boolean directed;

	/**
	 * @param directed 是否为包含To的定向请求
	 * @param types
	 */
	public PresenceMatcher(boolean directed, Set<String> types) {
		super(Presence.class);
		this.directed = directed;
		Set<PresenceType> convert = new HashSet<PresenceType>();
		for (String type : types) {
			convert.add(PresenceType.parse(type));
		}
		this.types = convert.toArray(new PresenceType[] {});
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && (!protocol.to() || this.directed) && PresenceType.parse(protocol.getType()).in(this.types);
	}
}
