package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年1月16日
 */
public enum ItemAffiliation {

	OUTCAST, NONE, MEMBER, ADMIN, OWNER;

	private static Map<Integer, ItemAffiliation> MAPPING = new HashMap<Integer, ItemAffiliation>();

	static {
		MAPPING.put(1, OUTCAST);
		MAPPING.put(2, NONE);
		MAPPING.put(3, MEMBER);
		MAPPING.put(4, ADMIN);
		MAPPING.put(5, OWNER);
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean contains(ItemAffiliation affiliation) {
		return this.ordinal() >= affiliation.ordinal();
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
		ItemAffiliation affiliation = MAPPING.get(num);
		return affiliation != null ? affiliation.toString() : OUTCAST.toString();
	}
}