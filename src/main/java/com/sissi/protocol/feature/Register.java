package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Stream;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Register extends Feature {

	public final static Register FEATURE = new Register();

	private final static String XMLNS = "http://jabber.org/features/iq-register";

	private Register() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
