package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.ErrorText;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ServerErrorText.NAME)
public class ServerErrorText implements ErrorText {

	public final static String NAME = "text";

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-streams";

	private String text;

	private String lang;

	public ServerErrorText() {

	}

	public ServerErrorText(String lang, String text) {
		super();
		this.lang = lang;
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}

	@XmlAttribute(name = "xml:lang")
	public String getLang() {
		return this.lang;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
