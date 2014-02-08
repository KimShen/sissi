package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月25日
 */
@Metadata(uri = Presence.XMLNS, localName = PresencePriority.NAME)
@XmlRootElement
public class PresencePriority {

	public final static PresencePriority ZERO = new PresencePriority("0");

	public final static String NAME = "priority";

	private String text;

	public PresencePriority() {
		super();
	}

	public PresencePriority(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public PresencePriority setText(String text) {
		this.text = text;
		return this;
	}
}
