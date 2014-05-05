package com.sissi.protocol.iq.session;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author Kim.shen 2013-10-20
 */
@Metadata(uri = Session.XMLNS, localName = Session.NAME)
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
