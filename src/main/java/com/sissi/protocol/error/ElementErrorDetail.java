package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月3日
 */
abstract public class ElementErrorDetail implements ErrorDetail {

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
