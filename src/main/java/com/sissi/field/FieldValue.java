package com.sissi.field;

import javax.xml.bind.annotation.XmlValue;

/**
 * @author kim 2013年12月16日
 */
abstract public class FieldValue implements Field<String> {

	private String value;

	public FieldValue() {
		super();
	}

	public FieldValue(String value) {
		super();
		this.value = value;
	}

	@XmlValue
	public String getValue() {
		return this.value;
	}

	public FieldValue setText(String text) {
		this.value = text;
		return this;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}
