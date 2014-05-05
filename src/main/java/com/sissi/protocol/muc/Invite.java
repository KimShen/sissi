package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = { XUser.XMLNS, Message.XMLNS }, localName = Invite.NAME)
@XmlRootElement(name = Invite.NAME)
public class Invite implements Collector {

	public final static String NAME = "invite";

	private String to;

	private String from;

	private Reason reason;

	private Continue continued;

	public Invite setFrom(String from) {
		this.from = from;
		this.to = null;
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return this.from;
	}

	@XmlAttribute
	public String getTo() {
		return this.to;
	}

	public Invite setTo(String to) {
		this.to = to;
		return this;
	}

	public String reason() {
		return this.reason != null ? this.reason.getText() : null;
	}

	public Invite reason(String reason) {
		this.reason = reason != null ? new Reason(reason) : null;
		return this;
	}

	@XmlElement
	public Reason getReason() {
		return this.reason;
	}

	public String continued() {
		return this.continued != null ? this.continued.getThread() : null;
	}
	
	public Invite continued(String thread) {
		this.continued = thread != null ? new Continue(thread) : null;
		return this;
	}

	@XmlElement
	public Continue getContinue() {
		return this.continued;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case Reason.NAME:
			this.reason = Reason.class.cast(ob);
			return;
		case Continue.NAME:
			this.continued = Continue.class.cast(ob);
			return;
		}
	}
}
