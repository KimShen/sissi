package com.sissi.protocol.iq.data;

/**
 * @author kim 2014年2月8日
 */
public enum XDataType {

	FORM, FORM_TYPE, SUBMIT, CANCEL, RESULT;

	public String toString() {
		return this == FORM_TYPE ? super.toString().toUpperCase() : super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == XDataType.parse(type);
	}

	public static XDataType parse(String value) {
		try {
			return XDataType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}
