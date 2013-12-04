package com.sissi.protocol.iq.bind;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-10-30
 */
@MappingMetadata(uri = "urn:ietf:params:xml:ns:xmpp-bind", localName = "resource")
@XmlRootElement
public class Resource extends Protocol {

	private String text;

	public Resource() {
		super();
	}

	public Resource(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean hasResource() {
		return this.text != null && !this.text.isEmpty();
	}
}
