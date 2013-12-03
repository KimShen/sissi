package com.sissi.protocol.iq.login;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.login.Register.Field;

/**
 * @author kim 2013年12月3日
 */
@XmlRootElement(name = "password")
public class Password implements Field {

	public final static Password FIELD = new Password();

	private final static String NAME = "password";

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
