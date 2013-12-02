package com.sissi.protocol.iq.login;

import org.apache.commons.codec.binary.Base64;
<<<<<<< HEAD
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
=======
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年11月26日
 */
public class Response extends Protocol {

<<<<<<< HEAD
	private final static Log LOG = LogFactory.getLog(Response.class);

=======
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public byte[] getResponse() {
<<<<<<< HEAD
		if (LOG.isDebugEnabled()) {
			String response = new String(Base64.decodeBase64(this.text));
			LOG.debug("Response: " + response);
			return response.getBytes();
		} else {
			return Base64.decodeBase64(this.text);
		}
=======
		return Base64.decodeBase64(this.text);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
	}
}
