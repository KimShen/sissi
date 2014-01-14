package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = Gone.NAME)
public class Gone implements ErrorDetail {

	public final static String NAME = "gone";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private String gone;

	public Gone() {

	}

	public Gone(String gone) {
		super();
		this.gone = gone;
	}

	@XmlValue
	public String getGone() {
		return gone;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
