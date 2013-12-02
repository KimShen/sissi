package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlValue;

/**
 * @author kim 2013-11-20
 */
public class Group {

	private String text;

	public Group() {
		super();
	}

	public Group(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
