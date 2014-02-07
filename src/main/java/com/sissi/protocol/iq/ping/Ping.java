package com.sissi.protocol.iq.ping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年1月8日
 */
@Metadata(uri = Ping.XMLNS, localName = Ping.NAME)
@XmlRootElement
public class Ping extends Protocol {

	public final static Ping PING = new Ping();

	public final static String XMLNS = "urn:xmpp:ping";

	public final static String NAME = "ping";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
