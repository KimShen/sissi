package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月16日
 */
public enum ItemAffiliation {

	OUTCAST, NONE, MEMBER, ADMIN, OWNER;

	private final static Map<Integer, ItemAffiliation> mapping = new HashMap<Integer, ItemAffiliation>();

	private final static Map<ItemAffiliation, PresenceType> actions = new HashMap<ItemAffiliation, PresenceType>();

	static {
		mapping.put(1, OUTCAST);
		mapping.put(2, NONE);
		mapping.put(3, MEMBER);
		mapping.put(4, ADMIN);
		mapping.put(5, OWNER);
		actions.put(OUTCAST, PresenceType.UNAVAILABLE);
		actions.put(NONE, PresenceType.AVAILABLE);
		actions.put(MEMBER, PresenceType.AVAILABLE);
		actions.put(ADMIN, PresenceType.AVAILABLE);
		actions.put(OWNER, PresenceType.AVAILABLE);
	}

	public String presence() {
		return actions.get(this).toString();
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean contains(ItemAffiliation affiliation) {
		return this.ordinal() >= affiliation.ordinal();
	}

	public boolean contains(String affiliation) {
		return this.contains(ItemAffiliation.parse(affiliation));
	}

	public boolean equals(String affiliation) {
		return this.toString().equals(affiliation);
	}

	public static ItemAffiliation parse(String affiliation) {
		try {
			return ItemAffiliation.valueOf(affiliation.toUpperCase());
		} catch (Exception e) {
			return ItemAffiliation.NONE;
		}
	}

	public static String toString(Integer num) {
		ItemAffiliation affiliation = mapping.get(num);
		return affiliation != null ? affiliation.toString() : OUTCAST.toString();
	}
}