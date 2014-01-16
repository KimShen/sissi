package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-11-2
 */
@MappingMetadata(uri = Presence.XMLNS, localName = PresenceStatus.NAME)
@XmlRootElement
public class PresenceStatus {

	public final static String NAME = "status";

	private String text;

	public PresenceStatus() {
		super();
	}

	public PresenceStatus(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public PresenceStatus setText(String text) {
		this.text = text;
		return this;
	}
}
