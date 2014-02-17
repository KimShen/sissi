package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2013-11-2
 */
@Metadata(uri = Presence.XMLNS, localName = PresenceShow.NAME)
@XmlRootElement
public class PresenceShow extends Presence {

	public final static String NAME = "show";

	private String text;

	public PresenceShow() {
		super();
	}

	public PresenceShow(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public PresenceShow setText(String text) {
		this.text = text;
		return this;
	}
}
