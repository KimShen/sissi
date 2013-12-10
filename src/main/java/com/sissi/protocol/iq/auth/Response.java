package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年11月26日
 */
@MappingMetadata(uri = "urn:ietf:params:xml:ns:xmpp-sasl", localName = "response")
@XmlRootElement
public class Response extends Protocol {

	private String text;

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getResponse() {
		return Base64.decodeBase64(this.text);
	}
}
