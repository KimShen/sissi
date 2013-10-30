package com.sisi.protocol.iq;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sisi.protocol.Feature;
import com.sisi.protocol.core.Stream;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Session extends Feature {

	public static final Session INSTANCE = new Session();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-session";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
