package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.context.OnlineStatus;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013-10-28
 */
@MappingMetadata(uri = "jabber:client", localName = "presence")
@XmlRootElement
public class Presence extends Protocol implements OnlineStatus, Collector {

	private final static String STATUS = "status";

	private final static String SHOW = "show";

	public static enum Type {

		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, ONLINE;

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

	private X x;

	private Show show;

	private Delay delay;

	private Status status;

	public Presence() {
		super();
	}

	public Presence(JID from, JID to, String show, String status, String type) {
		this.setShow(show != null ? new Show(show) : null).setStatus(status != null ? new Status(status) : null).setFrom(from.asString()).setTo(to.asString()).setType(type);
	}

	@XmlElement
	public X getX() {
		return x;
	}

	public Presence setX(X x) {
		this.x = x;
		return this;
	}

	public Presence setType(Type type) {
		super.setType(type.toString());
		return this;
	}

	public Presence setFrom(JID from) {
		super.setFrom(from.asString());
		return this;
	}

	@XmlTransient
	public String getTypeAsText() {
		return this.getType();
	}

	@XmlElement
	public Delay getDelay() {
		return this.delay;
	}

	public Presence setDelay(Delay delay) {
		this.delay = delay;
		return this;
	}

	@XmlElement(name = "show")
	public String getShowAsText() {
		return this.show != null ? this.show.getText() : null;
	}

	@XmlElement(name = "status")
	public String getStatusAsText() {
		return this.status != null ? this.status.getText() : null;
	}

	@XmlTransient
	public Show getShow() {
		return show;
	}

	public Presence setShow(Show show) {
		this.show = show;
		return this;
	}

	@XmlTransient
	public Status getStatus() {
		return status;
	}

	public Presence setStatus(Status status) {
		this.status = status;
		return this;
	}

	public Presence clear() {
		super.clear();
		super.setType((String) null);
		this.show = null;
		this.status = null;
		return this;
	}

	@Override
	public Presence asShow(String show) {
		this.setShow(show != null ? new Show(show) : null);
		return this;
	}

	@Override
	public Presence asStatus(String status) {
		this.setStatus(new Status(status != null ? status : null));
		return this;
	}

	@Override
	public Presence asType(String type) {
		return this.setType(Type.parse(type));
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case STATUS:
			this.setStatus((Status) ob);
			break;
		case SHOW:
			this.setShow((Show) ob);
			break;
		}
	}
}
