package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = NotAcceptable.NAME)
public class NotAcceptable implements ErrorDetail {

	public final static NotAcceptable DETAIL = new NotAcceptable();

	public final static String NAME = "not-acceptable";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private NotAcceptable() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
