package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Item;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-10-31
 */
@MappingMetadata(uri = Roster.XMLNS, localName = Item.NAME)
@XmlRootElement
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

	public GroupItem() {
		super();
	}

	public GroupItem(String jid, String name, String subscription, String group) {
		super(jid, name, subscription);
		this.group = group != null ? new Group(group) : null;
	}

	@XmlElement
	public Group getGroup() {
		return group;
	}

	public void set(String localname, Object ob) {
		this.group = Group.class.cast(ob);
	}

	public Action getAction() {
		return Action.parse(this.getSubscription());
	}
}
