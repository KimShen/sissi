package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-11-2
 */
@MappingMetadata(uri = Presence.XMLNS, localName = Status.NAME)
@XmlRootElement
public class Status {

	public final static String NAME = "status";

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
