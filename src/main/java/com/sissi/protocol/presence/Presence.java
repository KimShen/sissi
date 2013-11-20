package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

<<<<<<< HEAD
import com.sissi.context.JID;
import com.sissi.protocol.Protocol;
=======
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.group.X;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

/**
 * @author kim 2013-10-28
 */
@XmlRootElement
public class Presence extends Protocol {

<<<<<<< HEAD
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
=======
	public static enum Subscribe {

		SUBSCRIBE, SUBSCRIBED, REMOVE;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public static Subscribe parse(String subscribe) {
			if (subscribe == null) {
				return SUBSCRIBE;
			}
			return Subscribe.valueOf(subscribe.toUpperCase());
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
		}
	}

	private Show show;

	private Status status;

<<<<<<< HEAD
	public Presence() {
		super();
	}

	public Presence(JID from, JID to, Type type) {
		super.setFrom(from.asString());
		super.setTo(to.asString());
		super.setType(type.toString());
	}
=======
	private X x;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

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

<<<<<<< HEAD
=======
	@XmlElement(name = "x")
	public X getX() {
		return x;
	}

	public void setX(X x) {
		this.x = x;
	}

>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
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
<<<<<<< HEAD
		super.setType((String)null);
		this.show = null;
		this.status = null;
=======
		super.setType(null);
		this.show = null;
		this.status = null;
		this.x = null;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
		return this;
	}
}
