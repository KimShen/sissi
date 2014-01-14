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

	public static enum Xmlns {

		XMLNS_STREAM, XMLNS_STANZAS;

		public String toString() {
			switch (this) {
			case XMLNS_STREAM:
				return ServerErrorText.XMLNS_STREAM;
			case XMLNS_STANZAS:
				return ServerErrorText.XMLNS_STANZAS;
			}
			return null;
		}
	}

	public final static String NAME = "text";

	private final static String XMLNS_STREAM = "urn:ietf:params:xml:ns:xmpp-streams";

	private final static String XMLNS_STANZAS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private String text;

	private String lang;

	private Xmlns xmlns;

	public ServerErrorText() {

	}

	public ServerErrorText(String lang, String text, Xmlns xmlns) {
		super();
		this.lang = lang;
		this.text = text;
		this.xmlns = xmlns;
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
		return this.xmlns.toString();
	}
}
