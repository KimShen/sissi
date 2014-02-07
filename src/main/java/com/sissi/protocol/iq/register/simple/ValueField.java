package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlValue;

import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月16日
 */
abstract class ValueField implements Field<String> {

	private String value;

	@XmlValue
	public String getValue() {
		return this.value;
	}

	public void setText(String text) {
		this.value = text;
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
