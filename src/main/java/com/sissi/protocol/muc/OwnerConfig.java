package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年3月24日
 */
public enum OwnerConfig {

	NONE, REGISTER_FIRSET, REGISTER_LAST, REGISTER_ROOMNICK, REGISTER_URL, REGISTER_EMAIL, REGISTER_ALLOW, ROOMCONFIG_SUBJECT, ROOMCONFIG_MAXUSERS, ROOMCONFIG_AFFILIATION;

	private final static String prefix = "muc#";

	private final static Map<String, OwnerConfig> mapping = new HashMap<String, OwnerConfig>();

	static {
		for (OwnerConfig field : OwnerConfig.values()) {
			mapping.put(field.toString(), field);
		}
	}

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}

	public static boolean contains(String field) {
		return OwnerConfig.parse(field) != NONE;
	}

	public static OwnerConfig parse(String field) {
		try {
			return OwnerConfig.valueOf(field.replaceFirst(prefix, "").toUpperCase());
		} catch (Exception e) {
			return OwnerConfig.NONE;
		}
	}
}
