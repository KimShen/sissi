package com.sissi.protocol.muc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.protocol.presence.X;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XUser.XMLNS, localName = X.NAME)
@XmlType(namespace = XUser.XMLNS)
@XmlRootElement
public class XUser extends X implements Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	private List<Item> items;

	private List<ItemStatus> statuses;

	private String jid;

	public XUser() {
		super();
	}

	public XUser(JID jid) {
		this(jid.asStringWithBare());
	}

	public XUser(String jid) {
		super();
		this.jid = jid;
	}

	public XUser add(Item item) {
		if (this.items == null) {
			this.items = new ArrayList<Item>();
		}
		this.items.add(item);
		if (item.jid(this.jid)) {
			this.add(ItemStatus.STATUS_110);
		}
		return this;
	}

	public XUser add(ItemStatus code) {
		if (this.statuses == null) {
			this.statuses = new ArrayList<ItemStatus>();
		}
		this.statuses.add(code);
		return this;
	}

	@XmlElements({ @XmlElement(name = Item.NAME, type = Item.class) })
	public List<Item> getItems() {
		return this.items;
	}

	@XmlElements({ @XmlElement(name = ItemStatus.NAME, type = ItemStatus.class) })
	public List<ItemStatus> getStatuses() {
		return this.statuses;
	}

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}
