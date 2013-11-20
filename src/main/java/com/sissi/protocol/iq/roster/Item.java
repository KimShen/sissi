package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
<<<<<<< HEAD
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.protocol.Protocol;
=======

import com.sissi.protocol.Protocol;

>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
/**
 * @author kim 2013-10-31
 */
@XmlRootElement
public class Item extends Protocol {

<<<<<<< HEAD
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

=======
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	private String jid;

	private String name;

<<<<<<< HEAD
=======
	private String group;

>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	private String subscription;

	public Item() {
		super();
	}

	public Item(String jid, String name, String subscription, String group) {
		super();
		this.jid = jid;
		this.name = name;
		this.subscription = subscription;
<<<<<<< HEAD
		this.group = new Group(group);
=======
		this.group = group;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}

	@XmlAttribute
	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getSubscription() {
<<<<<<< HEAD
		return this.subscription;
=======
		return subscription;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

<<<<<<< HEAD
	@XmlTransient
	public Group getGroup() {
		return group;
	}

	@XmlElement(name = "group")
	public String getGp() {
		return group != null ? group.getText() : null;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Action getAction() {
		return Action.parse(this.getSubscription());
	}
=======
	@XmlElement
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
}
