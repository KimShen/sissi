package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item {

	public final static String NAME = "item";

	private MucGroupConfig config;

	private String affiliation;

	private String role;

	private String jid;

	private JID group;

	public Item() {
	}

	public Item(JID group, RelationMuc muc, MucGroupContext mucGroupContext) {
		super();
		this.group = group;
		this.role = muc.getRole();
		this.affiliation = muc.getAffiliaion();
		this.config = mucGroupContext.find(this.group);
	}

	public boolean equals(String jid) {
		return this.jid.equals(jid);
	}

	public boolean jid(String jid) {
		return this.jid != null && this.jid.equals(jid) ? true : false;
	}

	@XmlAttribute
	public String getJid() {
		return this.config.allowed(MucGroupConfig.HIDDEN, this.group) ? null : this.jid;
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
		return this.affiliation;
	}

	@XmlAttribute
	public String getRole() {
		return ItemRole.NONE.equals(this.role) ? this.config.mapping(this.getAffiliation()) : this.role;
	}
}
