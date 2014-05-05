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

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月14日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = XMucAdmin.NAME)
@XmlType(namespace = XMucAdmin.XMLNS)
@XmlRootElement
public class XMucAdmin extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc#admin";

	public final static String NAME = "query";

	private boolean valid = true;

	private boolean jids = true;

	private Set<String> snapshoot;

	private List<Item> items;

	private Item first;

	/**
	 * 有效性校验(回调)
	 * 
	 * @param valid
	 * @return
	 */
	XMucAdmin valid(boolean valid) {
		this.valid = this.valid ? valid : this.valid;
		return this;
	}

	XMucAdmin add(boolean jid) {
		this.jids = this.jids ? jid : this.jids;
		return this;
	}

	XMucAdmin add(String snapshoot) {
		if (this.snapshoot == null) {
			this.snapshoot = new HashSet<String>();
		}
		this.snapshoot.add(snapshoot);
		return this;
	}

	public Item first() {
		return this.first;
	}

	public String role() {
		return this.first().getRole();
	}

	public String affiliation() {
		return this.first().getAffiliation();
	}

	public boolean jids() {
		return this.jids;
	}

	public boolean vaild() {
		return this.valid;
	}

	public boolean item() {
		return this.getItem() != null && !this.getItem().isEmpty();
	}

	public boolean item(int size) {
		return this.item() && this.getItem().size() == size;
	}

	public boolean item(XMucAdminAction action) {
		Item item = this.first();
		return item != null ? (action == XMucAdminAction.ROLE ? item.getRole() != null : item.getAffiliation() != null) : false;
	}

	public boolean loop(String nick) {
		return this.snapshoot != null ? this.snapshoot.contains(nick) : false;
	}

	public XMucAdmin add(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		this.items.add(item.admin(this));
		this.first = this.first == null ? item : this.first;
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
