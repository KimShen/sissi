package com.sisi.protocol.iq;

import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-30
 */
public class Resource extends Protocol {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
