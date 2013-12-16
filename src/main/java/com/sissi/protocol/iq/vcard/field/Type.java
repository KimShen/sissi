package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月10日
 */
@MappingMetadata(uri = VCard.XMLNS, localName = Type.NAME)
@XmlRootElement(name = Type.NAME)
public class Type implements Field<String> {

	public final static String NAME = "TYPE";

	private String value;

	public Type() {
		super();
	}

	public Type(String text) {
		super();
		this.value = text;
	}

	@XmlValue
	public String getText() {
		return value;
	}

	public Type setText(String text) {
		this.value = text;
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		return this.value;
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
