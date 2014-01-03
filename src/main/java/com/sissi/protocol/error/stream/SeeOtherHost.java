package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = SeeOtherHost.NAME)
public class SeeOtherHost implements ErrorDetail {

	public final static String NAME = "see-other-host";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-streams";

	private String host;

	public SeeOtherHost() {
	}

	public SeeOtherHost(String host) {
		this.host = host;
	}

	@XmlValue
	public String getHost() {
		return host;
	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
