package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Success extends Protocol {

	public static final Success INSTANCE = new Success();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
