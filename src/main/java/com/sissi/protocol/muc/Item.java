package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
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

	private boolean hidden;

	public Item() {
	}

	public Item(RelationMuc muc) {
		this(false, muc);
	}

	public Item(boolean hidden, RelationMuc muc) {
		super();
		this.hidden = hidden;
		this.affiliation = muc.getAffiliaion();
		this.role = muc.getRole();
	}

	public boolean equals(String jid) {
		return this.jid.equals(jid);
	}

	public boolean jid(String jid) {
		return this.jid != null && this.jid.equals(jid) ? true : false;
	}

	@XmlAttribute
	public String getJid() {
		return this.hidden ? null : this.jid;
	}

	public Item setJid(JID jid) {
		this.setJid(jid.asString());
		return this;
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
