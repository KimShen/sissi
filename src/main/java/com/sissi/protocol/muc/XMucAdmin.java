package com.sissi.protocol.muc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月14日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = XMucAdmin.NAME)
@XmlType(namespace = XMucAdmin.XMLNS)
@XmlRootElement
public class XMucAdmin extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc#admin";

	public final static String NAME = "query";

	private Set<String> snapshoot;

	private List<Item> items;

	private String role;

	public Item first() {
		return this.item() ? this.getItem().get(0) : null;
	}

	public String role() {
		return this.role;
	}

	public boolean item() {
		return this.getItem() != null && !this.getItem().isEmpty();
	}

	public boolean item(int size) {
		return this.item() && this.getItem().size() == size;
	}

	public boolean loop(String nick) {
		return this.snapshoot != null ? this.snapshoot.contains(nick) : false;
	}

	public XMucAdmin add(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
			this.snapshoot = new HashSet<String>();
		}
		this.items.add(item);
		this.snapshoot.add(item.getNick());
		this.role = this.role == null ? item.getRole() : this.role;
		return this;
	}

	@XmlElements({ @XmlElement(name = Item.NAME, type = Item.class) })
	public List<Item> getItem() {
		return this.items;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public XMucAdmin clear() {
		super.clear();
		if (this.items != null) {
			this.items.clear();
		}
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add(Item.class.cast(ob));
	}
}
