package com.sissi.protocol.iq.session;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.MappingMetadata;

/**
 * @author Kim.shen 2013-10-20
 */
@MappingMetadata(uri = Session.XMLNS, localName = Session.NAME)
@XmlType(namespace = Session.XMLNS)
@XmlRootElement
public class Session extends Protocol {

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-session";

	public final static String NAME = "session";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
