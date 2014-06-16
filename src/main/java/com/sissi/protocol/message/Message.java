package com.sissi.protocol.message;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.offline.History;

/**
 * @author kim 2013-10-27
 */
@Metadata(uri = Message.XMLNS, localName = Message.NAME)
@XmlRootElement
public class Message extends Protocol implements Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "message";

	private boolean trace;

	private Body body;

	private XUser user;

	private XData data;

	private Delay delay;

	private Thread thread;

	private Subject subject;

	private History history;

	private AckRequest request;

	private AckReceived received;

	public Message() {
		this.trace = true;
	}

	private Message history(History history) {
		this.history = history;
		return this;
	}

	private Message request(AckRequest request) {
		this.request = request;
		return this;
	}

	private Message received(AckReceived received) {
		this.received = received;
		return this;
	}

	private Message x(Object x) {
		return XData.class == x.getClass() ? this.data(XData.class.cast(x)) : this.muc(XUser.class.cast(x));
	}

	public Message setType(MessageType type) {
		super.setType(type.toString());
		return this;
	}

	public String getType() {
		return super.getType() != null ? super.getType() : MessageType.NONE.toString();
	}

	public boolean type(MessageType... types) {
		for (MessageType type : types) {
			if (type.equals(this.getType())) {
				return true;
			}
		}
		return false;
	}

	public String getId() {
		return super.getId() != null || this.type(ProtocolType.ERROR, ProtocolType.RESULT) ? super.getId() : super.setId(UUID.randomUUID().toString()).getId();
	}

	public boolean delay() {
		return this.getDelay() != null;
	}

	@XmlElement
	public Delay getDelay() {
		return this.delay;
	}

	public Message delay(Delay delay) {
		this.delay = this.delay != null ? this.delay : delay;
		return this;
	}

	public boolean body() {
		return this.getBody() != null;
	}

	public Message body(String body) {
		this.body = body != null ? new Body(body) : null;
		return this;
	}

	public Message body(Body body) {
		this.body = body;
		return this;
	}

	@XmlElement
	public Body getBody() {
		return this.body;
	}

	public String thread() {
		return this.getThread() != null ? this.getThread().getText() : null;
	}

	public Message noneThread() {
		this.trace = false;
		return this;
	}

	public Message thread(Thread thread) {
		this.thread = thread;
		return this;
	}

	public Message setThread(String thread) {
		this.thread = thread != null ? new Thread(thread) : null;
		return this;
	}

	@XmlElement
	public Thread getThread() {
		return this.thread != null && this.thread.content() ? this.thread : (this.trace ? new Thread(UUID.randomUUID().toString()) : null);
	}

	public boolean subject() {
		return this.getSubject() != null;
	}

	public Message subject(String subject) {
		this.subject = subject != null ? new Subject(subject) : null;
		return this;
	}

	public Message subject(Subject subject) {
		this.subject = subject;
		return this;
	}

	@XmlElement
	public Subject getSubject() {
		return this.subject != null && this.subject.content() ? this.subject : null;
	}

	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	public Message muc(XUser x) {
		this.user = x;
		return this;
	}

	@XmlElement(name = XUser.NAME)
	public XUser getMuc() {
		return this.user;
	}

	public boolean data(String name) {
		return this.getData() != null && this.getData().findField(name, XField.class) != null;
	}

	public boolean dataType(String type) {
		return this.getData() != null && this.getData().type(XDataType.parse(type));
	}

	public Message data(XData x) {
		this.data = x;
		return this;
	}

	@XmlElement(name = XData.NAME)
	public XData getData() {
		return this.data;
	}

	public boolean invite() {
		return this.getMuc() != null && this.getMuc().invite();
	}

	public boolean decline() {
		return this.getMuc() != null && this.getMuc().decline();
	}

	public boolean request() {
		return this.getRequest() != null;
	}

	public Message request(boolean request) {
		return this.request(request ? AckRequest.REQUEST : null);
	}

	@XmlElement(name = AckRequest.NAME)
	public AckRequest getRequest() {
		return this.request;
	}

	public boolean received() {
		return this.getReceived() != null;
	}

	@XmlElement(name = AckReceived.NAME)
	public AckReceived getReceived() {
		return this.received;
	}

	public boolean history() {
		return this.getHistory() != null;
	}

	public History getHistory() {
		return this.history;
	}

	/**
	 * 是否同时存在Received/Request(冲突)
	 * 
	 * @return
	 */
	public boolean noneConflict() {
		return !this.received() || !this.request();
	}

	/**
	 * 是否同时存在Received/Body且存在Received.id
	 * 
	 * @return
	 */
	public boolean validReceived() {
		return this.received() ? (this.getBody() == null && this.getReceived().valid()) : true;
	}

	public boolean content() {
		return this.body != null && this.body.content();
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case XData.NAME:
			this.x(ob);
			return;
		case Body.NAME:
			this.body(Body.class.cast(ob));
			return;
		case Delay.NAME:
			this.delay(Delay.class.cast(ob));
			return;
		case Thread.NAME:
			this.thread(Thread.class.cast(ob));
			return;
		case Subject.NAME:
			this.subject(Subject.class.cast(ob));
			return;
		case History.NAME:
			this.history(History.class.cast(ob));
			return;
		case AckRequest.NAME:
			this.request(AckRequest.class.cast(ob));
			return;
		case AckReceived.NAME:
			this.received(AckReceived.class.cast(ob));
			return;
		}
	}
}
