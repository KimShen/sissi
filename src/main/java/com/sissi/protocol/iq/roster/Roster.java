package com.sissi.protocol.iq.roster;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;

/**
 * @author kim 2013-10-31
 */
@XmlRootElement(name = "query")
public class Roster extends Protocol implements Collector {

	public static enum Subscription {

		TO, BOTH, NONE;

		public String toString() {
			return super.toString().toLowerCase();
		}
		
		public Boolean equals(String subscribe) {
			return this == Subscription.parse(subscribe);
		}

		public static Subscription parse(String subscribe) {
			if (subscribe == null || subscribe.toLowerCase() == "remove") {
				return NONE;
			}
			return Subscription.valueOf(subscribe.toUpperCase());
		}
	}

	private final static Log LOG = LogFactory.getLog(Roster.class);

	private final static String XMLNS = "jabber:iq:roster";

	private List<Item> item;

	public Roster() {
		super();
	}

	public Roster(Item item) {
		this.add(item);
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Roster add(Item item) {
		if (this.item == null) {
			this.item = new ArrayList<Item>();
		}
		LOG.debug("Add item: " + item);
		this.item.add(item);
		return this;
	}

	public Roster add(String jid, String name, String subscription, String group) {
		if (this.item == null) {
			this.item = new ArrayList<Item>();
		}
		this.add(new Item(jid, name, subscription, group));
		return this;
	}

	@XmlElements({ @XmlElement(name = "item", type = Item.class) })
	public List<Item> getItem() {
		return item;
	}

	public Item getFirstItem() {
		return this.item != null && !this.item.isEmpty() ? this.item.get(0) : null;
	}

	@Override
	public void set(String localName, Object protocol) {
		if (localName.equals("item")) {
			Item item = (Item) protocol;
			LOG.debug("Add item: " + item);
			this.add(item);
		}
	}

	public Protocol clear() {
		super.clear();
		this.item = null;
		return this;
	}
}
