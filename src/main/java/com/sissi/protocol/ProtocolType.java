package com.sissi.protocol;

/**
 * @author kim 2014年1月16日
 */
public enum ProtocolType {

	NONE, SET, GET, RESULT, ERROR, CANCEL, WAIT, AUTH, CONTINUE, MODIFY;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == ProtocolType.parse(type);
	}

	public boolean in(ProtocolType... types) {
		for (ProtocolType type : types) {
			if (this == type) {
				return true;
			}
		}
		return false;
	}

	public static ProtocolType parse(String value) {
		try {
			return ProtocolType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			return NONE;
		}
	}
}