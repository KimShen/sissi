package com.sissi.protocol.iq.register.form;

/**
 * @author kim 2013年12月16日
 */
public enum InputType {

	TEXT_SINGLE, LIST_SINGLE, HIDDEN, FORM;

	private final static String TEXT_SINGLE_TEXT = "text-single";

	private final static String LIST_SINGLE_TEXT = "list-single";

	public String toString() {
		switch (this) {
		case TEXT_SINGLE:
			return TEXT_SINGLE_TEXT;
		case LIST_SINGLE:
			return LIST_SINGLE_TEXT;
		default:
			return super.toString().toLowerCase();
		}
	}

	public static InputType parse(String type) {
		switch (type) {
		case TEXT_SINGLE_TEXT:
			return TEXT_SINGLE;
		case LIST_SINGLE_TEXT:
			return LIST_SINGLE;
		default:
			return InputType.valueOf(type.toUpperCase());
		}
	}
}