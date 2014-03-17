package com.sissi.protocol.muc;

import java.util.ArrayList;
import java.util.List;

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

	private List<Item> items;

	public ItemRole role() {
		return this.item() ? ItemRole.parse(this.items.get(0).getRole()) : ItemRole.NONE;
	}

	public boolean item() {
		return this.getItem() != null && !this.getItem().isEmpty();
	}

	public boolean item(int size) {
		return this.item() && this.items.size() == size;
	}

	public XMucAdmin add(Item change) {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		this.items.add(change);
		return this;
	}

	public Item first() {
		return this.item() ? this.items.get(0) : null;
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
