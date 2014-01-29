package com.sissi.protocol.message;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
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

	private Thread thread;

	private Subject subject;

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

	@XmlElement
	public Thread getThread() {
		return this.thread != null && this.thread.hasContent() ? this.thread : new Thread(UUID.randomUUID().toString());
	}

	@XmlElement
	public Subject getSubject() {
		return this.subject;
	}

	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	public Message setSubject(Subject subject) {
		this.subject = subject;
		return this;
	}

	public Message setThread(Thread thread) {
		this.thread = thread;
		return this;
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
		switch (localName) {
		case Body.NAME:
			this.setBody(Body.class.cast(ob));
			return;
		case Thread.NAME:
			this.setThread(Thread.class.cast(ob));
			return;
		case Subject.NAME:
			this.setSubject(Subject.class.cast(ob));
			return;
		}
	}
}
