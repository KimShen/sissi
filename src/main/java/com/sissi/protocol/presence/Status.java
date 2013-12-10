package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-11-2
 */
@MappingMetadata(uri = "jabber:client", localName = "status")
@XmlRootElement
public class Status extends Protocol {

	private String text;

	public Status() {
		super();
	}

	public Status(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Status setText(String text) {
		this.text = text;
		return this;
	}
}
