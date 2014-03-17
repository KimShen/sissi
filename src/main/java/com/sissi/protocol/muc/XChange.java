package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.context.JID;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月14日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = XChange.NAME)
@XmlRootElement(name = XChange.NAME)
public class XChange implements Collector {

	private final static Map<String, PresenceType> actions = new HashMap<String, PresenceType>();

	static {
		actions.put(ItemRole.NONE.toString(), PresenceType.UNAVAILABLE);
		actions.put(ItemRole.VISITOR.toString(), PresenceType.AVAILABLE);
		actions.put(ItemRole.PARTICIPANT.toString(), PresenceType.AVAILABLE);
		actions.put(ItemRole.MODERATOR.toString(), PresenceType.AVAILABLE);
	}

	public final static String NAME = "item";

	private JID group;

	private Item item;

	private String role;

	private String nick;

	private XReason reason;

	public Presence presence() {
		return new Presence().setType(actions.get(this.getRole())).setFrom(this.group);
	}

	public JID group(JID jid) {
		return this.group = jid.resource(this.getNick());
	}

	public Item item(JID jid) {
		return this.item != null ? this.item.actor(jid) : this.item().actor(jid);
	}

	private Item item() {
		return this.item = new Item().reason(this.getReason());
	}

	public boolean role() {
		return this.role != null;
	}

	@XmlAttribute
	public String getRole() {
		return this.role;
	}

	public XChange setRole(String role) {
		this.role = role;
		return this;
	}

	@XmlAttribute
	public String getNick() {
		return this.nick;
	}

	public XChange setNick(String nick) {
		this.nick = nick;
		return this;
	}

	public String reason() {
		return this.getReason() != null ? this.getReason().getText() : null;
	}

	@XmlElement
	public XReason getReason() {
		return this.reason;
	}

	@Override
	public void set(String localName, Object ob) {
		this.reason = XReason.class.cast(ob);
	}
}
