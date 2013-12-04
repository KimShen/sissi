package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement
public class Auth extends Protocol {

	private final static String XMLNS = "http://jabber.org/features/iq-auth";

	private String mechanism;

	private String text;

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public String getText() {
		return text;
	}

	public Auth setText(String text) {
		this.text = text;
		return this;
	}

	public String getMechanism() {
		return mechanism;
	}

	public Auth setMechanism(String mechanism) {
		this.mechanism = mechanism;
		return this;
	}
}
