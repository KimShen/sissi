package com.sissi.protocol.iq.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kim 2014年2月8日
 */
public enum XDataType {

	FORM, SUBMIT, CANCEL, RESULT;

	private final static Set<String> values = new HashSet<String>();

	static {
		for (XDataType each : XDataType.values()) {
			values.add(each.toString().toUpperCase());
		}
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == XDataType.parse(type);
	}

	public static XDataType parse(String value) {
		String type = value != null ? value.toUpperCase() : value;
		return values.contains(type) ? XDataType.valueOf(type) : null;
	}
}
