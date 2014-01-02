package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.sissi.protocol.Feature;
import com.sissi.protocol.Required;

/**
 * @author kim 2013年12月17日
 */
public class Starttls implements Feature {

	public final static Starttls FEATURE = new Starttls();

	public final static String NAME = "starttls";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-tls";

	private Starttls() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	@XmlElement
	public Required getRequired() {
		return null;
	}
}
