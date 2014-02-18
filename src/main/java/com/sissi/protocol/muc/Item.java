package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item {

	public final static String NAME = "item";

	private String affiliation;

	private String role;

	private String jid;

	public Item() {
		super();
	}

	public Item(RelationMuc muc) {
		super();
		this.affiliation = muc.getAffiliaion();
		this.role = muc.getRole();
	}

	@XmlAttribute
	public String getJid() {
		return jid;
	}

	public Item setJid(String jid) {
		this.jid = jid;
		return this;
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
