package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2014年3月3日
 */
abstract class Ack {

	public final static String XMLNS = "urn:xmpp:receipts";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
