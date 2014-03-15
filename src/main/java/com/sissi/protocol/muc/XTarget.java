package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月14日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = XTarget.NAME)
@XmlRootElement(name = XTarget.NAME)
public class XTarget implements Collector {

	public final static String NAME = "item";

	private String role;

	private String nick;

	private XReason reason;

	public boolean role() {
		return this.role != null;
	}

	@XmlAttribute
	public String getRole() {
		return this.role;
	}

	public XTarget setRole(String role) {
		this.role = role;
		return this;
	}

	@XmlAttribute
	public String getNick() {
		return this.nick;
	}

	public XTarget setNick(String nick) {
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
