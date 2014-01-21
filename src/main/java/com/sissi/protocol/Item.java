package com.sissi.protocol;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2013-10-31
 */
abstract public class Item {

	public final static String NAME = "item";

	private String jid;

	private String name;

	public Item() {
		super();
	}

	public Item(String jid, String name) {
		super();
		this.jid = jid;
		this.name = name;
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
	public String getName() {
		return this.name == null || this.name.isEmpty() ? null : this.name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}
}
