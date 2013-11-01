package com.sisi.protocol.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sisi.context.JID;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
@XmlRootElement
public class Message extends Protocol {

	private Body body;

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
	public String getContent() {
		return this.body.getText();
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
