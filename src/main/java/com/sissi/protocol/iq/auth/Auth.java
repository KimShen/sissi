package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.MappingMetadata;

/**
 * @author Kim.shen 2013-10-16
 */
@MappingMetadata(uri = Auth.XMLNS, localName = Auth.NAME)
@XmlRootElement
public class Auth extends Protocol {

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	public final static String NAME = "auth";

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
