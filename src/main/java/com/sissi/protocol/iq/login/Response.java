package com.sissi.protocol.iq.login;

import org.apache.commons.codec.binary.Base64;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年11月26日
 */
public class Response extends Protocol {

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public byte[] getResponse() {
		return Base64.decodeBase64(this.text);
	}
}
