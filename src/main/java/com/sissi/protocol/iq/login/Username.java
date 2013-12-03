package com.sissi.protocol.iq.login;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.Field;

/**
 * @author kim 2013年12月3日
 */
@XmlRootElement(name = "username")
public class Username implements Field {

	public final static Username FIELD = new Username();
	
	private final static String NAME = "username";

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
