package com.sissi.protocol.iq.register;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = "jabber:iq:register", localName = "password")
@XmlRootElement(name = "password")
public class Password implements Field {

	public final static Password FIELD = new Password();

	private final static String NAME = "password";

	private String text;

	@XmlValue
	public String getValue() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Boolean isEmpty() {
		return this.getValue() == null;
	}
}
