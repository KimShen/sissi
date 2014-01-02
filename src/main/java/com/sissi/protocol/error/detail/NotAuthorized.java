package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = NotAuthorized.NAME)
public class NotAuthorized implements ErrorDetail {

	public final static NotAuthorized DETAIL = new NotAuthorized();

	public final static String NAME = "not-authorized";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private NotAuthorized() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
