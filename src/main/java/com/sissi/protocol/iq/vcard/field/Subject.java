package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年3月28日
 */
@XmlType(namespace = VCard.XMLNS)
@XmlRootElement(name = Subject.NAME)
public class Subject implements Field<String> {

	public final static String NAME = "SUBJECT";

	private String value;

	public Subject() {
		super();
	}

	public Subject(String text) {
		super();
		this.value = text;
	}

	@XmlValue
	public String getValue() {
		return value.toString();
	}

	public Subject setText(String text) {
		this.value = text;
		return this;
	}

	public boolean hasContent() {
		return this.value != null;
	}

	@Override
	public String getName() {
		return NAME;
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
