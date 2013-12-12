package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = "jabber:iq:register", localName = "password")
@XmlRootElement(name = "password")
public class Password implements Field<String> {

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
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}
}
