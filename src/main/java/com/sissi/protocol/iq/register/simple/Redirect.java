package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2014年5月10日
 */
@XmlType(namespace = Register.XMLNS)
@XmlRootElement(name = Redirect.NAME)
public class Redirect implements Field<String> {

	public final static String NAME = "x";

	private final static String XMLNS = "jabber:x:oob";

	private String url;

	public Redirect() {
		super();
	}

	public Redirect(String url) {
		super();
		this.url = url;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@XmlElement(name = "url")
	public String getValue() {
		return this.url;
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
