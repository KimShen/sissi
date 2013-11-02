package com.sissi.protocol.core;

/**
 * @author kim 2013-11-1
 */
public class Body {

	public String text;

	public Body() {
		super();
	}

	public Body(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean hasContent() {
		return this.text != null && !this.text.isEmpty();
	}
}
