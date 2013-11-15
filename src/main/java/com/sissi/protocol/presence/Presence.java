package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.group.X;

/**
 * @author kim 2013-10-28
 */
@XmlRootElement
public class Presence extends Protocol {

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
		}
	}

	private Show show;

	private Status status;

	private X x;

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

	@XmlElement(name = "x")
	public X getX() {
		return x;
	}

	public void setX(X x) {
		this.x = x;
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
		super.setType(null);
		this.show = null;
		this.status = null;
		this.x = null;
		return this;
	}
}
