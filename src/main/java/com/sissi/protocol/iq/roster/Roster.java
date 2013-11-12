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

	private final static Log LOG = LogFactory.getLog(Roster.class);

	private final static String XMLNS = "jabber:iq:roster";

	private List<Item> item;

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public void add(Item item) {
		if (this.item == null) {
			this.item = new ArrayList<Item>();
		}
		LOG.debug("Add item: " + item);
		this.item.add(item);
	}

	public void add(String jid, String name, String subscription, String group) {
		if (this.item == null) {
			this.item = new ArrayList<Item>();
		}
		this.add(new Item(jid, name, subscription, group));
	}

	@XmlElements({ @XmlElement(name = "item", type = Item.class) })
	public List<Item> getItem() {
		return item;
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
