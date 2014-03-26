package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.protocol.Error;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.muc.MucItem;
import com.sissi.ucenter.muc.RelationMuc;

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

	private boolean refuse;

	private boolean hidden;

	private XReason reason;

	private XActor actor;

	private Error error;

	private JID group;

	public Item() {
	}

	public Item(boolean hidden, RelationMuc relation) {
		super();
		this.relation(relation).hidden = hidden;
	}

	private Presence presence(XMucAdminAction action, String affiliation) {
		this.refuse = !ItemAffiliation.parse(this.getAffiliation()).contains(affiliation);
		return new Presence().setType(this.refuse ? PresenceType.UNAVAILABLE.toString() : XMucAdminAction.AFFILIATION == action ? ItemAffiliation.parse(this.getAffiliation()).presence() : ItemRole.parse(this.getRole()).presence()).setFrom(group).cast(Presence.class);
	}

	public Item relation(RelationMuc relation) {
		this.jid = relation.jid();
		this.role = relation.role();
		this.nick = relation.name();
		this.affiliation = relation.affiliation();
		return this;
	}

	public boolean refuse() {
		return this.refuse;
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

	public Presence presence(JID group, String affiliation) {
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
		this.jid = jid;
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.hidden ? null : this.jid;
	}

	public Item setAffiliation(String affiliation) {
		this.affiliation = affiliation;
		return this;
	}

	@XmlAttribute
	public String getAffiliation() {
		return this.affiliation;
	}

	public Item setRole(String role) {
		this.role = role;
		return this;
	}

	@XmlAttribute
	public String getRole() {
		return this.refuse ? ItemRole.NONE.toString() : this.role;
	}

	public Item setNick(String nick) {
		this.nick = nick;
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
			this.reason = new XReason(reason);
		}
		this.getReason().setText(reason);
		return this;
	}

	@XmlElement
	public XReason getReason() {
		return this.reason;
	}

	public Item actor(JID actor) {
		if (this.actor == null) {
			this.actor = new XActor();
		}
		this.actor.jid(actor);
		return this;
	}

	@XmlElement
	public XActor getActor() {
		return this.hidden ? null : this.actor;
	}

	@Override
	public void set(String localName, Object ob) {
		this.reason = XReason.class.cast(ob);
	}

	public <T extends MucItem> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
