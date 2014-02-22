package com.sissi.protocol.message;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2013-10-27
 */
@Metadata(uri = Message.XMLNS, localName = Message.NAME)
@XmlRootElement
public class Message extends Protocol implements Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "message";

	private XUser x;

	private Body body;

	private Delay delay;

	private Thread thread;

	private Subject subject;

	public Message() {
		super();
	}

	public Message setType(MessageType type) {
		super.setType(type.toString());
		return this;
	}

	public String getId() {
		return super.getId() != null ? super.getId() : UUID.randomUUID().toString();
	}

	@XmlElement
	public Delay getDelay() {
		return this.delay;
	}

	public Message setDelay(Delay delay) {
		this.delay = delay;
		return this;
	}

	public Message setBody(Body body) {
		this.body = body;
		return this;
	}

	@XmlElement
	public Body getBody() {
		return this.body;
	}

	public Message setThread(Thread thread) {
		this.thread = thread;
		return this;
	}

	@XmlElement
	public Thread getThread() {
		return this.thread != null && this.thread.hasContent() ? this.thread : new Thread(UUID.randomUUID().toString());
	}

	public Message setSubject(Subject subject) {
		this.subject = subject;
		return this;
	}

	@XmlElement
	public Subject getSubject() {
		return this.subject;
	}

	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	public Message setX(XUser x) {
		this.x = x;
		return this;
	}

	@XmlElement
	public XUser getX() {
		return this.x;
	}

	public boolean hasContent() {
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
