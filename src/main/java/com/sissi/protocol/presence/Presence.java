package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.context.MyPresence;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-28
 */
@XmlRootElement
public class Presence extends Protocol implements MyPresence {

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
			if (subscribe == null) {
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

	public Presence(JID from, JID to, String show, String status, String type) {
		super.setFrom(from.asString());
		super.setTo(to.asString());
		super.setType(type);
		if (show != null) {
			this.setShow(new Show(show));
		}
		if (status != null) {
			this.setStatus(new Status(status));
		}
	}

	public Presence setType(Type type) {
		super.setType(type.toString());
		return this;
	}

	public Presence setFrom(JID from) {
		super.setFrom(from);
		return this;
	}

	@XmlTransient
	public String getTypeText() {
		return this.getType();
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

	public Presence clear() {
		super.clear();
		super.setType((String) null);
		this.show = null;
		this.status = null;
		return this;
	}

	@Override
	public Presence setShowText(String show) {
		if (show != null) {
			this.setShow(new Show(show));
		}
		return this;
	}

	@Override
	public Presence setStatusText(String status) {
		if (status != null) {
			this.setStatus(new Status(status));
		}
		return this;
	}

	@Override
	public MyPresence setTypeText(String type) {
		return (MyPresence) this.setType(Type.parse(type));
	}
}
