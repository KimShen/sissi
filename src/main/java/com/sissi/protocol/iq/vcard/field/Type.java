package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月10日
 */
@MappingMetadata(uri = "vcard-temp", localName = "TYPE")
@XmlRootElement(name = "TYPE")
public class Type implements Field<String> {

	public final static String NAME = Type.class.getSimpleName().toLowerCase();

	private String text;

	public Type() {
		super();
	}

	public Type(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return text;
	}

	public Type setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		return this.text;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}
}
