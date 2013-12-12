package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-11-2
 */
@MappingMetadata(uri = "jabber:client", localName = "show")
@XmlRootElement
public class Show{

	private String text;

	public Show() {
		super();
	}

	public Show(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Show setText(String text) {
		this.text = text;
		return this;
	}
}
