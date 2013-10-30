package com.sisi.protocol.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sisi.protocol.Protocol;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	private String notAuthorized;

	@XmlElement(name = "not-authorized", nillable = true)
	public String getNotAuthorized() {
		return notAuthorized;
	}

	public void setNotAuthorized(String notAuthorized) {
		this.notAuthorized = notAuthorized;
	}
}
