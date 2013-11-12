package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-28
 */
@XmlRootElement
public class Presence extends Protocol {

	public static enum Subscribe {

		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBED;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public static Subscribe parse(String subscribe) {
			return Subscribe.valueOf(subscribe.toUpperCase());
		}
	}

	private Show show;

	private Status status;

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
		super.setType(null);
		return this;
	}
}
