package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.vcard.StringVCardField;

/**
 * @author kim 2013年12月5日
 */
@XmlRootElement
public class Option extends StringVCardField {

	public Option() {
	}

	public Option(String name, String value) {
		super(name, value);
	}

	@Override
	@XmlAttribute(name = "label")
	public String getName() {
		return super.getName();
	}

	@XmlElement
	public String getValue() {
		return super.getValue();
	}
}
