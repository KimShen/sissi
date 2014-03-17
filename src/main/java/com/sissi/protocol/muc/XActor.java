package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月14日
 */
@XmlRootElement(name = XActor.NAME)
public class XActor {

	public final static String NAME = "actor";

	private String jid;

	public XActor() {
		super();
	}

	public XActor(JID jid) {
		super();
		this.jid = jid.asString();
	}

	public XActor jid(JID jid) {
		this.jid = jid.asString();
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.jid;
	}
}
