package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-11-2
 */
@MappingMetadata(uri = Presence.NAME, localName = Show.NAME)
@XmlRootElement
public class Show {

	public final static String NAME = "show";

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
