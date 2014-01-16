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

	private String text;

	private String lang;

	private String xmlns;

	public ServerErrorText() {

	}

	public ServerErrorText(String lang, String text, String xmlns) {
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
		return this.xmlns;
	}
}
