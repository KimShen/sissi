package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年11月26日
 */
@Metadata(uri = Auth.XMLNS, localName = Response.NAME)
@XmlRootElement
public class Response extends Protocol {

	public final static String NAME = "response";

	private String text;

	public Response setText(String text) {
		this.text = text;
		return this;
	}

	public byte[] getResponse() {
		return Base64.decodeBase64(this.text);
	}
}
