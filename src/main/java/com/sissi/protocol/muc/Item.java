package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item {

	public final static String NAME = "item";

	private MucConfig config;

	private RelationMuc relation;

	private String jid;

	public Item() {
	}

	public Item(String jid, RelationMuc relation, MucConfig config) {
		super();
		this.jid = jid;
		this.config = config;
		this.relation = relation;
	}

	@XmlAttribute
	public String getJid() {
		return this.jid;
	}

	@XmlAttribute
	public String getAffiliation() {
		return this.relation.getAffiliation();
	}

	@XmlAttribute
	public String getRole() {
		return ItemRole.NONE.equals(this.relation.getRole()) ? this.config.mapping(this.getAffiliation()) : this.relation.getRole();
	}
}
