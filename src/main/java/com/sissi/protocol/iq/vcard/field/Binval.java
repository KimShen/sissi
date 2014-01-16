package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = VCard.XMLNS, localName = Binval.NAME)
@XmlRootElement(name = Binval.NAME)
public class Binval implements Field<String> {

	public final static String NAME = "BINVAL";

	private StringBuffer value;

	public Binval() {
		super();
		this.value = new StringBuffer();
	}

	public Binval(String text) {
		super();
		this.value = new StringBuffer(text);
	}

	@XmlValue
	public String getValue() {
		return value.toString();
	}

	public Binval setText(String text) {
		this.value.append(text);
		return this;
	}

	public Boolean hasContent() {
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
	public Boolean hasChild() {
		return false;
	}
}
