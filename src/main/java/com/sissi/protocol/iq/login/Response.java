package com.sissi.protocol.iq.login;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年11月26日
 */
public class Response extends Protocol {

	private final static Log LOG = LogFactory.getLog(Response.class);

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public byte[] getResponse() {
		if (LOG.isDebugEnabled()) {
			String afterDecoder = new String(Base64.decodeBase64(this.text));
			return afterDecoder.getBytes();
		} else {
			return Base64.decodeBase64(this.text);
		}
	}
}
