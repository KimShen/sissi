package com.sissi.protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kim 2014年1月16日
 */
public enum ProtocolType {

	SET, GET, RESULT, ERROR, CANCEL, WAIT, AUTH, CONTINUE, MODIFY;

	private final static Set<String> values = new HashSet<String>();

	static {
		for (ProtocolType each : ProtocolType.values()) {
			values.add(each.toString().toUpperCase());
		}
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public Boolean equals(String type) {
		return this == ProtocolType.parse(type);
	}

	public Boolean in(ProtocolType... types) {
		for (ProtocolType type : types) {
			if (this == type) {
				return true;
			}
		}
		return false;
	}

	public static ProtocolType parse(String value) {
		String type = value != null ? value.toUpperCase() : value;
		return values.contains(type) ? ProtocolType.valueOf(type) : null;
	}
}