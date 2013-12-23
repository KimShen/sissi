package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Item;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-10-31
 */
@MappingMetadata(uri = Roster.XMLNS, localName = Item.NAME)
@XmlType(namespace = Roster.XMLNS)
@XmlRootElement(name = Item.NAME)
public class GroupItem extends Item implements Collector {

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

	private String subscription;

	public GroupItem() {
		super();
	}

	public GroupItem(String jid, String name, String subscription, String group) {
		super(jid, name);
		this.subscription = subscription;
		this.group = group != null ? new Group(group) : null;
	}

	@XmlElement
	public Group getGroup() {
		return group;
	}

	public void set(String localname, Object ob) {
		this.group = Group.class.cast(ob);
	}

	@XmlAttribute
	public String getSubscription() {
		return this.subscription;
	}

	public GroupItem setSubscription(String subscription) {
		this.subscription = subscription;
		return this;
	}

	public Action getAction() {
		return Action.parse(this.getSubscription());
	}
}
