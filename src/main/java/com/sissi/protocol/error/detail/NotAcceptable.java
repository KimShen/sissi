package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.Detail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = "not-acceptable")
public class NotAcceptable implements Detail {

	public final static NotAcceptable DETAIL = new NotAcceptable();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private NotAcceptable() {

	}

	@Override
	public String getXmlns() {
		return XMLNS;
	}
}
