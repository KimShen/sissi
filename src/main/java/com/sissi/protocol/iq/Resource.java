package com.sissi.protocol.iq;

import com.sissi.protocol.Protocol;

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
	
	public Boolean hasResource(){
		return this.text != null && !this.text.isEmpty();
	}
}
