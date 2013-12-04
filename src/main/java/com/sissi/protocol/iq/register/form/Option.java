package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.RegisterContext.Field;

/**
 * @author kim 2013年12月5日
 */
@XmlRootElement
public class Option implements Field {

	private String name;

	private String value;

	public Option() {

	}

	public Option(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	@XmlAttribute(name = "label")
	public String getName() {
		return name;
	}

	@Override
	@XmlElement(name = "value")
	public String getText() {
		return value;
	}

}
