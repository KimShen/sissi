package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "BINVAL")
@XmlRootElement(name = "BINVAL")
public class Binval implements Field<String> {
	
	public final static String NAME = Binval.class.getSimpleName().toLowerCase();

	private StringBuffer text;

	public Binval() {
		super();
		this.text = new StringBuffer();
	}

	public Binval(String text) {
		super();
		this.text = new StringBuffer(text);
	}

	@XmlValue
	public String getValue() {
		return text.toString();
	}

	public Binval setText(String text) {
		this.text.append(text);
		return this;
	}

	public Boolean hasContent() {
		return this.text != null;
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
