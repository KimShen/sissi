package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年11月26日
 */
@MappingMetadata(uri = Auth.XMLNS, localName = Response.NAME)
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
