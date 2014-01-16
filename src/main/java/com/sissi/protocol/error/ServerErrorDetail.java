package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月16日
 */
abstract public class ServerErrorDetail implements ErrorDetail {

	protected final static String XMLNS_ELEMENT = "urn:ietf:params:xml:ns:xmpp-stanzas";

	protected final static String XMLNS_STREAM = "urn:ietf:params:xml:ns:xmpp-streams";

	private final String xmlns;

	public ServerErrorDetail() {
		super();
		this.xmlns = null;
	}

	public ServerErrorDetail(String xmlns) {
		super();
		this.xmlns = xmlns;
	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return this.xmlns;
	}
}
