package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.ucenter.muc.MucItem;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item implements MucItem {

	public final static String NAME = "item";

	private RelationMuc relation;

	private boolean hidden;

	private XReason reason;

	private XActor actor;

	public Item() {
	}

	public Item(boolean hidden, RelationMuc relation) {
		super();
		this.hidden = hidden;
		this.relation = relation;
	}

	public Item hidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}

	public Item relation(RelationMuc relation) {
		this.relation = relation;
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.hidden ? null : this.relation.jid();
	}

	@XmlAttribute
	public String getAffiliation() {
		return this.relation.affiliation();
	}

	@XmlAttribute
	public String getRole() {
		return this.relation.role();
	}

	public Item reason(XReason reason) {
		this.reason = reason;
		return this;
	}

	@XmlElement
	public XReason getReason() {
		return this.reason;
	}

	public Item actor(XActor actor) {
		this.actor = actor;
		return this;
	}

	@XmlElement
	public XActor getActor() {
		return this.hidden ? null : this.actor;
	}
}
