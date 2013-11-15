package com.sissi.protocol.presence.group;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-13
 */
@XmlType(namespace = X.XMLNS)
@XmlRootElement
public class Item extends Protocol {

	private String affiliation;

	private String role;

	public Item() {
		super();
	}

	public Item(String affiliation, String role) {
		super();
		this.affiliation = affiliation;
		this.role = role;
	}

	@XmlAttribute
	public String getAffiliation() {
		return affiliation;
	}

	@XmlAttribute
	public String getRole() {
		return role;
	}
}
