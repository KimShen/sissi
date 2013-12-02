package com.sissi.protocol.iq.login;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;

/**
 * @author kim 2013年11月28日
 */
@XmlRootElement
public class Register extends Feature {

	private final static String XMLNS = "http://jabber.org/features/iq-register";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}