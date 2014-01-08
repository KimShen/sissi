package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月7日
 */
@XmlRootElement(name = NotAllowed.NAME)
public class NotAllowed implements ErrorDetail {

	public final static NotAllowed DETAIL = new NotAllowed();

	public final static String NAME = "not-allowed";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private NotAllowed() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
