package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月14日
 */
@XmlRootElement(name = Actor.NAME)
public class Actor {

	public final static String NAME = "actor";

	private String jid;

	public Actor() {
		super();
	}

	public Actor(JID jid) {
		super();
		this.jid = jid.asString();
	}

	public Actor jid(JID jid) {
		this.jid = jid.asString();
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.jid;
	}
}
