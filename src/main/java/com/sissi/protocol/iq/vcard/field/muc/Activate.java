package com.sissi.protocol.iq.vcard.field.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2014年2月10日
 */
@Metadata(uri = VCard.XMLNS, localName = Activate.NAME)
@XmlRootElement(name = Activate.NAME)
public class Activate implements Field<String> {

	public final static String NAME = "ACTIVATE";

	private String value;

	public Activate() {
		super();
	}

	public Activate(String text) {
		super();
		this.value = text;
	}

	@XmlValue
	public String getValue() {
		return value.toString();
	}

	public Activate setText(String text) {
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
