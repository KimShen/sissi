package com.sissi.protocol.presence;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.context.StatusClauses;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013-10-28
 */
@MappingMetadata(uri = Presence.XMLNS, localName = Presence.NAME)
@XmlRootElement
public class Presence extends Protocol implements com.sissi.context.Status, Fields, Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "presence";

	public static enum Type {

		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, AVAILABLE;

		public String toString() {
			if (AVAILABLE == this) {
				return null;
			}
			return super.toString().toLowerCase();
		}

		public Boolean equals(String type) {
			return this == Type.parse(type);
		}

		public static Type parse(String subscribe) {
			if (subscribe == null) {
				return AVAILABLE;
			}
			return Type.valueOf(subscribe.toUpperCase());
		}
	}

	private BeanFields fields;

	private Show show;

	private Delay delay;

	private Status status;

	private Priority priority;

	public Presence() {
		super();
		this.fields = new BeanFields(false);
	}

	public Presence(String from, String to, String show, String status, String type, String avator) {
		this();
		this.setShow(show != null ? show : null).setStatus(status != null ? status : null).setAvator(avator != null ? avator : null).setFrom(from).setTo(to).setType(type);
	}

	private XVCard findX() {
		return XVCard.class.cast(this.fields != null ? this.fields.findField(XVCard.NAME, XVCard.class) : null);
	}

	@XmlTransient
	public Integer getPriority() {
		return this.priority != null ? Integer.parseInt(this.priority.getText()) : 0;
	}

	public Presence setType(Type type) {
		super.setType(type.toString());
		return this;
	}

	public Presence setFrom(JID from) {
		super.setFrom(from.asString());
		return this;
	}

	@XmlElement
	public Delay getDelay() {
		return this.delay;
	}

	public Presence setDelay(Delay delay) {
		this.delay = delay;
		return this;
	}

	@XmlTransient
	public String getTypeAsText() {
		return this.getType();
	}

	@XmlElement(name = Show.NAME)
	public String getShowAsText() {
		return this.show != null ? this.show.getText() : null;
	}

	@XmlElement(name = Status.NAME)
	public String getStatusAsText() {
		return this.status != null ? this.status.getText() : null;
	}

	@XmlTransient
	public String getAvatorAsText() {
		XVCard x = this.findX();
		XVCardPhoto xp = x != null ? Fields.class.cast(x).findField(XVCardPhoto.NAME, XVCardPhoto.class) : null;
		return xp != null ? xp.getValue() : null;
	}

	@XmlTransient
	public Show getShow() {
		return show;
	}

	public Presence setShow(Show show) {
		this.show = show;
		return this;
	}

	public Presence setShow(String show) {
		this.show = new Show(show);
		return this;
	}

	public Presence setStatus(Status status) {
		this.status = status;
		return this;
	}

	public Presence setStatus(String status) {
		this.status = new Status(status);
		return this;
	}

	@XmlElements({ @XmlElement(name = XVCard.NAME, type = XVCard.class), @XmlElement(name = XUser.NAME, type = XUser.class) })
	public List<Field<?>> getFields() {
		return this.fields.getFields();
	}

	public Presence clear() {
		super.clear();
		super.setType((String) null);
		this.show = null;
		this.status = null;
		this.fields = null;
		return this;
	}

	public Presence setAvator(String type) {
		this.add(new XVCard().add(new XVCardPhoto(type)));
		return this;
	}
	
	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case X.NAME:
			this.add(Field.class.cast(ob));
			return;
		case Status.NAME:
			this.setStatus((Status) ob);
			return;
		case Show.NAME:
			this.setShow((Show) ob);
			return;
		case Priority.NAME:
			this.priority = Priority.class.cast(ob);
			return;
		}
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.fields.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return this.fields.isEmbed();
	}

	@Override
	public Presence add(Field<?> field) {
		if (this.fields == null) {
			this.fields = new BeanFields(false);
		}
		this.fields.add(field);
		return this;
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.fields.findField(name, clazz);
	}

	@Override
	public Presence setStatus(String type, String show, String status, String avator) {
		this.setShow(show).setStatus(status).setAvator(avator).setType(type);
		return null;
	}

	@Override
	public StatusClauses getStatusClauses() {
		return new PresenceClauses();
	}

	private class PresenceClauses implements StatusClauses {

		@Override
		public String find(String key) {
			switch (key) {
			case StatusClauses.KEY_TYPE:
				return Presence.this.getType();
			case StatusClauses.KEY_SHOW:
				return Presence.this.getShowAsText();
			case StatusClauses.KEY_STATUS:
				return Presence.this.getStatusAsText();
			case StatusClauses.KEY_AVATOR:
				return Presence.this.getAvatorAsText();
			}
			return null;
		}
	}
}
