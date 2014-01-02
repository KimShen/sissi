package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = InvaildNamespace.NAME)
public class InvaildNamespace implements ErrorDetail {

	public final static InvaildNamespace DETAIL = new InvaildNamespace();

	public final static String NAME = "invalid-namespace";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private InvaildNamespace() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}