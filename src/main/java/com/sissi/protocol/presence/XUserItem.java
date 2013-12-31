package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月30日
 */
@XmlRootElement
public class XUserItem {

	public final static String NAME = "item";

	private String jid;

	private String role;

	private String affiliation;

	public XUserItem() {
		super();
	}

	public XUserItem(String jid, String role, String affiliation) {
		super();
		this.jid = jid;
		this.role = role;
		this.affiliation = affiliation;
	}

	public String getJid() {
		return this.jid;
	}

	@XmlAttribute
	public String getRole() {
		return role;
	}

	@XmlAttribute
	public String getAffiliation() {
		return affiliation;
	}
}
