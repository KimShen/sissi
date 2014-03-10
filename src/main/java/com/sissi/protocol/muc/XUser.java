package com.sissi.protocol.muc;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.protocol.presence.X;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.muc.MucStatus;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XUser.XMLNS, localName = X.NAME)
@XmlType(namespace = XUser.XMLNS)
@XmlRootElement
public class XUser extends X implements MucStatus, Collector, Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	private XInvite invite;

	private XDecline decline;

	private XPassword password;

	private boolean hidden;

	private Item item;

	private JID jid;

	public XUser() {
		super();
	}

	public XUser(JID jid, boolean hidden) {
		this.jid = jid;
		this.hidden = hidden;
	}

	private Set<ItemStatus> statuses;

	public boolean hidden() {
		return this.hidden;
	}

	public boolean owner() {
		return this.jid != null ? this.jid.same(this.item.getJid()) : false;
	}

	public XUser add(String code) {
		if (this.statuses == null) {
			this.statuses = new HashSet<ItemStatus>();
		}
		this.statuses.add(ItemStatus.parse(code));
		return this;
	}

	public XUser setItem(Item item) {
		this.item = item;
		return this;
	}

	@XmlElement
	public Item getItem() {
		return this.item;
	}

	@XmlElement
	public XInvite getInvite() {
		return this.invite;
	}

	public boolean invite() {
		return this.getInvite() != null;
	}

	@XmlElement
	public XDecline getDecline() {
		return this.decline;
	}

	public boolean decline() {
		return this.getDecline() != null;
	}

	@XmlElement
	public XPassword getPassword() {
		return this.password;
	}

	public boolean password() {
		return this.getPassword() != null;
	}

	@XmlElements({ @XmlElement(name = ItemStatus.NAME, type = ItemStatus.class) })
	public Set<ItemStatus> getStatuses() {
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

	public <T extends MucStatus> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case XInvite.NAME:
			this.invite = XInvite.class.cast(ob);
			return;
		case XDecline.NAME:
			this.decline = XDecline.class.cast(ob);
			return;
		case XPassword.NAME:
			this.password = XPassword.class.cast(ob);
			return;
		}
	}
}
