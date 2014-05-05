package com.sissi.protocol.iq.roster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-31
 */
@Metadata(uri = Roster.XMLNS, localName = Roster.NAME)
@XmlRootElement(name = Roster.NAME)
public class Roster extends Protocol implements Collector {

	public final static String XMLNS = "jabber:iq:roster";

	public final static String NAME = "query";

	private List<GroupItem> item;

	private GroupItem firstItem;

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

	@XmlElements({ @XmlElement(name = GroupItem.NAME, type = GroupItem.class) })
	public List<GroupItem> getItem() {
		return this.item;
	}

	public GroupItem getFirstItem() {
		return this.firstItem != null ? this.firstItem : this.getFirstItemCached();
	}

	@Override
	public void set(String localName, Object protocol) {
		this.add(GroupItem.class.cast(protocol));
	}

	public Roster clear() {
		super.clear();
		this.item = null;
		return this;
	}

	public Roster trimItem(Integer item) {
		if (this.item != null && this.item.size() > item) {
			GroupItem need = this.getFirstItem();
			this.item.clear();
			this.item.add(need);
		}
		return this;
	}

	private GroupItem getFirstItemCached() {
		return !this.item.isEmpty() ? (this.firstItem = this.item.get(0)) : (this.firstItem = null);
	}
}
