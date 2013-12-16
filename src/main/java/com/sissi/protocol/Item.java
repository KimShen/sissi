package com.sissi.protocol;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2013-10-31
 */
abstract public class Item {

	public final static String NAME = "item";

	private String jid;

	private String name;

	private String subscription;

	public Item() {
		super();
	}

	public Item(String jid, String name, String subscription) {
		super();
		this.jid = jid;
		this.name = name;
		this.subscription = subscription;
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
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	@XmlAttribute
	public String getSubscription() {
		return this.subscription;
	}

	public Item setSubscription(String subscription) {
		this.subscription = subscription;
		return this;
	}
}
