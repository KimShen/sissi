package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月16日
 */
public enum ItemRole {

	NONE, VISITOR, PARTICIPANT, MODERATOR;

	private final static Map<Integer, ItemRole> mapping = new HashMap<Integer, ItemRole>();

	private final static Map<ItemRole, PresenceType> actions = new HashMap<ItemRole, PresenceType>();

	static {
		mapping.put(1, NONE);
		mapping.put(2, VISITOR);
		mapping.put(3, PARTICIPANT);
		mapping.put(4, MODERATOR);
		actions.put(NONE, PresenceType.UNAVAILABLE);
		actions.put(VISITOR, PresenceType.AVAILABLE);
		actions.put(MODERATOR, PresenceType.AVAILABLE);
		actions.put(PARTICIPANT, PresenceType.AVAILABLE);
	}

	public String presence() {
		return actions.get(this).toString();
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean contains(String role) {
		return this.contains(ItemRole.parse(role));
	}

	public boolean contains(ItemRole role) {
		return this.ordinal() >= role.ordinal();
	}

	public boolean equals(String role) {
		return this.toString().equals(role);
	}

	public static ItemRole parse(String role) {
		try {
			return ItemRole.valueOf(role.toUpperCase());
		} catch (Exception e) {
			return ItemRole.NONE;
		}
	}

	public static String max(String role, String other) {
		return ItemRole.max(ItemRole.parse(role), ItemRole.parse(other)).toString();
	}

	public static ItemRole max(ItemRole role, ItemRole other) {
		return role.contains(other) ? role : other;
	}

	public static String toString(Integer num) {
		ItemRole sub = mapping.get(num);
		return sub != null ? sub.toString() : NONE.toString();
	}
}