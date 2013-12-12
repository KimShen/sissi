package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-11-20
 */
@MappingMetadata(uri = "jabber:iq:roster", localName = "group")
@XmlRootElement
public class Group {

	private String text;

	public Group() {
		super();
	}

	public Group(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
