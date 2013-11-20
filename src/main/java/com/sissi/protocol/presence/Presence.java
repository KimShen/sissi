package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-28
 */
@XmlRootElement
public class Presence extends Protocol {

	public static enum Type {

		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBED, UNAVAILABLE, ONLINE;

		public String toString() {
			if (ONLINE == this) {
				return null;
			}
			return super.toString().toLowerCase();
		}

		public Boolean equals(String type) {
			return this == Type.parse(type);
		}

		public static Type parse(String subscribe) {
			if(subscribe == null){
				return ONLINE;
			}
			return Type.valueOf(subscribe.toUpperCase());
		}
	}

	private Show show;

	private Status status;

	public Presence() {
		super();
	}

	public Presence(JID from, JID to, Type type) {
		super.setFrom(from.asString());
		super.setTo(to.asString());
		super.setType(type.toString());
	}

	@XmlElement(name = "show")
	public String getShowText() {
		return this.show != null ? this.show.getText() : null;
	}

	@XmlElement(name = "status")
	public String getStatusText() {
		return this.status != null ? this.status.getText() : null;
	}

	@XmlTransient
	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	@XmlTransient
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Protocol clear() {
		super.clear();
		super.setType((String)null);
		this.show = null;
		this.status = null;
		return this;
	}
}
