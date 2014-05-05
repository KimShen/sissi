package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.Sissi;

/**
 * @author kim 2014年2月17日
 */
@Metadata(uri = Roster.XMLNS, localName = RosterNickname.NAME)
@XmlRootElement(name = RosterNickname.NAME)
public class RosterNickname {

	public final static String NAME = "nickname";

	private String text;

	public RosterNickname() {
		super();
	}

	public RosterNickname(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}

	@XmlAttribute
	public String getXmlns() {
		return Sissi.XMLNS;
	}
}
