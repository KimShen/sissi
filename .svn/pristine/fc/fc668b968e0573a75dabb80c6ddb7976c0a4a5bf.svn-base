package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
@XmlRootElement
public class Message extends Protocol {

	private Body body;

	private Delay delay;

	public Message() {
		super();
	}

	public Message(JID from, JID to, String body) {
		super();
		super.setFrom(from != null ? from.asString() : null);
		super.setTo(to != null ? to.asString() : null);
		this.body = new Body(body);
	}

	@XmlElement(name = "body")
	public String getBodyText() {
		return this.body != null ? this.body.getText() : null;
	}

	@XmlElement(name = "delay")
	public Delay getDelay() {
		return delay;
	}

	public void setDelay(Delay delay) {
		this.delay = delay;
	}

	@XmlTransient
	public Body getBody() {
		return this.body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Boolean hasContent() {
		return this.body != null && this.body.hasContent();
	}
}
