package com.sissi.protocol.iq.session;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlType(namespace = Stream.NAMESPACE)
@XmlRootElement
public class Session extends Protocol {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-session";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
