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
		return this.toString().equals(type);
	}

	public static ProtocolType parse(String value) {
		try {
			return ProtocolType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			return NONE;
		}
	}
}