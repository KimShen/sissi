package com.sissi.protocol.presence.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item {

	public final static String NAME = "item";

	private String affiliation;

	private String role;

	public Item() {
		super();
	}

	public Item(ItemAffiliation affiliation, ItemRole role) {
		super();
		this.affiliation = affiliation.toString();
		this.role = role.toString();
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
