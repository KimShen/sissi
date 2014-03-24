package com.sissi.protocol.iq.register.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年3月24日
 */
public enum RegisterField {

	NONE, REGISTER_FIRSET, REGISTER_LAST, REGISTER_ROOMNICK, REGISTER_URL, REGISTER_EMAIL, REGISTER_ALLOW;

	private final static String prefix = "muc#";

	private final static Map<String, RegisterField> mapping = new HashMap<String, RegisterField>();

	static {
		for (RegisterField field : RegisterField.values()) {
			mapping.put(field.toString(), field);
		}
	}

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}

	public static boolean contains(String field) {
		return RegisterField.parse(field) != NONE;
	}

	public static RegisterField parse(String field) {
		try {
			return RegisterField.valueOf(field.replaceFirst(prefix, "").toUpperCase());
		} catch (Exception e) {
			return RegisterField.NONE;
		}
	}
}
