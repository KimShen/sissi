package com.sissi.ucenter.relation.muc.register;

import java.util.HashMap;
import java.util.Map;

import com.sissi.protocol.muc.XMuc;

/**
 * @author kim 2014年4月23日
 */
public enum RegisterConfig {

	NONE, URL, LAST, FIRST, ROOMNICK, FAQENTRY, EMAIL, ALLOW;

	public final static String XMLNS = XMuc.XMLNS + "#register";

	private final static String prefix = "muc#register_";

	private final static Map<String, RegisterConfig> mapping = new HashMap<String, RegisterConfig>();

	static {
		for (RegisterConfig field : RegisterConfig.values()) {
			mapping.put(field.toString(), field);
		}
	}

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}

	public static boolean contains(String field) {
		return RegisterConfig.parse(field) != NONE;
	}

	public static RegisterConfig parse(String field) {
		try {
			return RegisterConfig.valueOf(field.replaceFirst(prefix, "").toUpperCase());
		} catch (Exception e) {
			return RegisterConfig.NONE;
		}
	}
}
