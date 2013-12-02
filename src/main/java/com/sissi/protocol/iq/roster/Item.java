package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-31
 */
@XmlRootElement
public class Item extends Protocol {

	public static enum Action {

		ADD, REMOVE;

		public static Action parse(String action) {
			return action == null ? ADD : Action.valueOf(action.toUpperCase());
		}

		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private Group group;

	private String jid;

	private String name;

	private String subscription;

	public Item() {
		super();
	}

	public Item(String jid, String name, String subscription, String group) {
		super();
		this.jid = jid;
		this.name = name;
		this.subscription = subscription;
		this.group = new Group(group);
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

	@XmlElement
	public Group getGroup() {
		return group;
	}

	public Item setGroup(Group group) {
		this.group = group;
		return this;
	}

	public Action getAction() {
		return Action.parse(this.getSubscription());
	}
}
