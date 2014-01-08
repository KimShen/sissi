package com.sissi.protocol.iq.ping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月8日
 */
@XmlRootElement
public class Ping extends Protocol {

	public final static Ping PING = new Ping();

	public final static String XMLNS = "urn:xmpp:ping";

	public final static String NAME = "ping";

	private Ping() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
