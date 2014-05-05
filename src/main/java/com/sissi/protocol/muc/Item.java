package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Error;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = Item.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item implements MucItem, Collector {

	public final static String NAME = "item";

	private String role;

	private String nick;

	private String jid;

	private String affiliation;

	private boolean limit;

	private boolean hidden;

	private XMucAdmin admin;

	private Reason reason;

	private Actor actor;

	private Error error;

	private JID group;

	public Item() {
	}

	public Item(boolean hidden, String nick, MucRelation relation) {
		super();
		this.relation(relation).hidden = hidden;
		this.nick = nick;
	}

	public Item(boolean hidden, MucRelation relation) {
		super();
		this.relation(relation).hidden = hidden;
	}

	Item admin(XMucAdmin admin) {
		this.admin = admin;
		return this;
	}

	private Presence presence(XMucAdminAction action, String affiliation) {
		this.limit = !ItemAffiliation.parse(this.getAffiliation()).contains(affiliation);
		return new Presence().setType(this.limit ? PresenceType.UNAVAILABLE.toString() : XMucAdminAction.AFFILIATION == action ? ItemAffiliation.parse(this.getAffiliation()).presence() : ItemRole.parse(this.getRole()).presence()).setFrom(this.group).cast(Presence.class);
	}

	public Item relation(MucRelation relation) {
		this.jid = relation.jid();
		this.role = relation.role();
		this.nick = relation.name();
		this.affiliation = relation.affiliation();
		return this;
	}

	public boolean forbidden() {
		return this.limit;
	}

	public boolean error(Error error) {
		return (this.error = error) != null;
	}

	public Error error() {
		return this.error;
	}

	public Item hidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}

	public Presence presence() {
		return this.presence(XMucAdminAction.ROLE, null);
	}

	public Presence presence(String affiliation) {
		return this.presence(XMucAdminAction.AFFILIATION, affiliation);
	}

	public JID group(JID jid) {
		return this.group = jid.resource(this.getNick());
	}

	public Item jid(JID jid) {
		this.jid = jid.asStringWithBare();
		return this;
	}

	public Item setJid(String jid) {
		this.admin.add((this.jid = jid) != null);
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.hidden ? null : this.jid;
	}

	public Item setAffiliation(String affiliation) {
		this.affiliation = affiliation;
		if (this.admin != null) {
			this.admin.valid(this.getRole() == null);
		}
		return this;
	}

	@XmlAttribute
	public String getAffiliation() {
		return this.affiliation;
	}

	public Item setRole(String role) {
		this.role = role;
		if (this.admin != null) {
			this.admin.valid(this.getAffiliation() == null);
		}
		return this;
	}

	@XmlAttribute
	public String getRole() {
		return this.limit ? ItemRole.NONE.toString() : this.role;
	}

	public Item setNick(String nick) {
		this.admin.add(this.nick = nick);
		return this;
	}

	@XmlAttribute
	public String getNick() {
		return this.nick;
	}

	public String reason() {
		return this.getReason() != null ? this.getReason().getText() : null;
	}

	public Item reason(String reason) {
		if (this.getReason() == null) {
			this.reason = new Reason(reason);
		}
		this.getReason().setText(reason);
		return this;
	}

	@XmlElement
	public Reason getReason() {
		return this.reason;
	}

	public Item actor(JID actor) {
		if (this.actor == null) {
			this.actor = new Actor();
		}
		this.actor.jid(actor);
		return this;
	}

	@XmlElement
	public Actor getActor() {
		return this.hidden ? null : this.actor;
	}

	@Override
	public void set(String localName, Object ob) {
		this.reason = Reason.class.cast(ob);
	}

	public <T extends MucItem> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
