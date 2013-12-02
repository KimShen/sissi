package com.sissi.protocol.iq.login;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol {
	
	public final static Failure INSTANCE = new Failure();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
