package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-10-27
 */
@MappingMetadata(uri = Message.XMLNS, localName = Message.NAME)
@XmlRootElement
public class Message extends Protocol implements Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "message";

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

	@XmlElement
	public Delay getDelay() {
		return delay;
	}

	public Message setDelay(Delay delay) {
		this.delay = delay;
		return this;
	}

	@XmlElement
	public Body getBody() {
		return this.body;
	}

	public Message setBody(Body body) {
		this.body = body;
		return this;
	}

	public Boolean hasContent() {
		return this.body != null && this.body.hasContent();
	}

	@Override
	public void set(String localName, Object ob) {
		this.setBody(Body.class.cast(ob));
	}
}
