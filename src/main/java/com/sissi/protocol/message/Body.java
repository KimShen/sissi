package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author kim 2013-11-1
 */
@XmlRootElement
public class Body {

	private String text;

	public Body() {
		super();
	}

	public Body(String text) {
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

	public Boolean hasContent() {
		return this.text != null && !this.text.isEmpty();
	}
}
