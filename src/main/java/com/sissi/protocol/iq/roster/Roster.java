package com.sissi.protocol.iq.roster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-10-31
 */
@MappingMetadata(uri = Roster.XMLNS, localName = Roster.NAME)
@XmlRootElement(name = Roster.NAME)
public class Roster extends Protocol implements Collector {

	public static enum Subscription {

		TO, BOTH, NONE;

		private final static String REMOVE = "remove";

		public String toString() {
			return super.toString().toLowerCase();
		}

		public Boolean equals(String subscribe) {
			return this == Subscription.parse(subscribe);
		}

		public static Subscription parse(String subscribe) {
			if (subscribe == null || subscribe.toLowerCase() == REMOVE) {
				return NONE;
			}
			return Subscription.valueOf(subscribe.toUpperCase());
		}
	}

	public final static String XMLNS = "jabber:iq:roster";

	public final static String NAME = "query";

	private List<GroupItem> item;

	public Roster() {
		super();
	}

	public Roster(GroupItem item) {
		this.add(item);
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Roster add(GroupItem item) {
		if (this.item == null) {
			this.item = new ArrayList<GroupItem>();
		}
		this.item.add(item);
		return this;
	}

	@XmlElements({ @XmlElement(name = "item", type = GroupItem.class) })
	public List<GroupItem> getItem() {
		return item;
	}

	public GroupItem getFirstItem() {
		return this.item != null && !this.item.isEmpty() ? this.item.get(0) : null;
	}

	@Override
	public void set(String localName, Object protocol) {
		this.add(GroupItem.class.cast(protocol));
	}

	public Roster close() {
		super.close();
		this.item = null;
		return this;
	}
}
