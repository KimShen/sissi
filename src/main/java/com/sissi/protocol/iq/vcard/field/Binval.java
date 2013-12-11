package com.sissi.protocol.iq.vcard.field;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "vcard-temp", localName = "BINVAL")
@XmlRootElement(name = "BINVAL")
public class Binval implements Field {

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

	public void setText(String text) {
		this.text.append(text);
	}

	public Boolean hasContent() {
		return this.text != null;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Boolean isEmpty() {
		return this.getValue() == null;
	}
}
