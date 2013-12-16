package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error.Detail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = NotAcceptable.NAME)
public class NotAcceptable implements Detail {

	public final static NotAcceptable DETAIL = new NotAcceptable();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	public final static String NAME = "not-acceptable";

	private NotAcceptable() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
