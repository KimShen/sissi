package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年1月16日
 */
public enum ItemAffiliation {

	OUTCAST, MEMBER, ADMIN, OWNER;

	private static Map<Integer, ItemAffiliation> MAPPING = new HashMap<Integer, ItemAffiliation>();

	static {
		MAPPING.put(1, OUTCAST);
		MAPPING.put(2, MEMBER);
		MAPPING.put(3, ADMIN);
		MAPPING.put(3, OWNER);
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String affiliation) {
		return this == ItemAffiliation.parse(affiliation);
	}

	public boolean all(ItemAffiliation... affiliations) {
		for (ItemAffiliation affiliation : affiliations) {
			if ((this != affiliation) && ((this.ordinal() & affiliation.ordinal()) != 0)) {
				return false;
			}
		}
		return true;
	}

	public static ItemAffiliation parse(String affiliation) {
		try {
			return ItemAffiliation.valueOf(affiliation.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(Integer num) {
		ItemAffiliation sub = MAPPING.get(num);
		return sub != null ? sub.toString() : OUTCAST.toString();
	}
}