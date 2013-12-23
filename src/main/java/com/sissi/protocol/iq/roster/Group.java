package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-11-20
 */
@MappingMetadata(uri = Roster.XMLNS, localName = Group.NAME)
@XmlRootElement
public class Group {

	public final static String NAME = "group";

	private String value;

	public Group() {
		super();
	}

	public Group(String text) {
		super();
		this.value = text;
	}

	@XmlValue
	public String getValue() {
		return value;
	}

	public Group setText(String text) {
		this.value = text;
		return this;
	}
}
