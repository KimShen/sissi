package com.sissi.protocol.iq.roster;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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

	private Set<Group> groups;

	private String subscription;

	public GroupItem() {
		super();
	}

	public GroupItem(String jid, String name, String subscription, String[] groups) {
		super(jid, name);
		this.subscription = subscription;
		if (groups != null) {
			for (String group : groups) {
				this.add(new Group(group));
			}
		}
	}

	private GroupItem add(Group group) {
		if (this.groups == null) {
			this.groups = new HashSet<Group>();
		}
		this.groups.add(group);
		return this;
	}

	@XmlElements({ @XmlElement(name = Group.NAME, type = Group.class) })
	public Set<Group> getGroup() {
		return this.groups;
	}

	public void set(String localname, Object ob) {
		this.add(Group.class.cast(ob));
	}

	@XmlAttribute
	public String getSubscription() {
		return this.subscription;
	}

	public GroupItem setSubscription(String subscription) {
		this.subscription = subscription;
		return this;
	}

	public GroupAction getAction() {
		return GroupAction.parse(this.getSubscription());
	}

	public Item trimName(Integer length) {
		if (super.getName() != null) {
			super.setName(super.getName().substring(0, length));
		}
		return this;
	}
}
