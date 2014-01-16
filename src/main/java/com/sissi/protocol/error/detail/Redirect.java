package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = Redirect.NAME)
public class Redirect implements ErrorDetail {

	public final static String NAME = "redirect";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private String redirect;

	public Redirect() {

	}

	public Redirect(String redirect) {
		super();
		this.redirect = redirect;
	}

	@XmlValue
	public String getRedirect() {
		return redirect;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
