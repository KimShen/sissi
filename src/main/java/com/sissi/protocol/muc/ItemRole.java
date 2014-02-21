package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年1月16日
 */
public enum ItemRole {

	NONE, VISITOR, PARTICIPANT, MODERATOR;

	private static Map<Integer, ItemRole> MAPPING = new HashMap<Integer, ItemRole>();

	static {
		MAPPING.put(1, NONE);
		MAPPING.put(2, VISITOR);
		MAPPING.put(3, PARTICIPANT);
		MAPPING.put(4, MODERATOR);
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String role) {
		return this == ItemRole.parse(role);
	}

	public static ItemRole parse(String role) {
		try {
			return role != null ? ItemRole.valueOf(role.toUpperCase()) : null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(Integer num) {
		ItemRole sub = MAPPING.get(num);
		return sub != null ? sub.toString() : VISITOR.toString();
	}
}