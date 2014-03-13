package com.sissi.protocol.iq.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2013年12月16日
 */
public enum XFieldType {

	NONE, JID_SINGLE, JID_MULTI, TEXT_SINGLE, TEXT_MULTI, TEXT_PRIVATE, LIST_SINGLE, LIST_MULTI, HIDDEN, FIXED, BOOLEAN;

	private static Map<String, XFieldType> direction = new HashMap<String, XFieldType>();

	private static Map<XFieldType, String> reverse = new HashMap<XFieldType, String>();

	static {
		for (XFieldType type : XFieldType.values()) {
			String value = type.name().toLowerCase().replace("_", "-");
			direction.put(value, type);
			reverse.put(type, value);
		}
	}

	public String toString() {
		return this == NONE ? null : reverse.get(this);
	}

	public static XFieldType parse(String type) {
		XFieldType xType = direction.get(type);
		return xType != null ? xType : NONE;
	}
}