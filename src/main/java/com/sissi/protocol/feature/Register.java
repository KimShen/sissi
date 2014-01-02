package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;
import com.sissi.protocol.Required;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement
public class Register implements Feature {

	public final static Register FEATURE = new Register();

	public final static String NAME = "register";

	private final static String XMLNS = "http://jabber.org/features/iq-register";

	private Register() {

	}

	@Override
	@XmlElement
	public Required getRequired() {
		return null;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
