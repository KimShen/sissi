package com.sissi.protocol.presence;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Field.Fields;
import com.sissi.ucenter.vcard.ListVCardFields;

/**
 * @author kim 2013-10-28
 */
@MappingMetadata(uri = "jabber:client", localName = "presence")
@XmlRootElement
public class Presence extends Protocol implements com.sissi.context.JIDContext.Status, Fields, Collector {

	private final static String X = "x";

	private final static String STATUS = "status";

	private final static String SHOW = "show";

	public static enum Type {

		SUBSCRIBE, SUBSCRIBED, UNSUBSCRIBE, UNSUBSCRIBED, UNAVAILABLE, ONLINE;

		public String toString() {
			if (ONLINE == this) {
				return null;
			}
			return super.toString().toLowerCase();
		}

		public Boolean equals(String type) {
			return this == Type.parse(type);
		}

		public static Type parse(String subscribe) {
			if (subscribe == null) {
				return ONLINE;
			}
			return Type.valueOf(subscribe.toUpperCase());
		}
	}

	private final ListVCardFields vCardFields = new ListVCardFields(false);

	private Show show;

	private Delay delay;

	private Status status;

	public Presence() {
		super();
	}

	public Presence(JID from, JID to, String show, String status, String type) {
		this.setShow(show != null ? new Show(show) : null).setStatus(status != null ? new Status(status) : null).setFrom(from.asStringWithBare()).setTo(to.asStringWithBare()).setType(type);
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

	@XmlElement(name = "show")
	public String getShowAsText() {
		return this.show != null ? this.show.getText() : null;
	}

	@XmlElement(name = "status")
	public String getStatusAsText() {
		return this.status != null ? this.status.getText() : null;
	}

	@XmlTransient
	public Show getShow() {
		return show;
	}

	public Presence setShow(Show show) {
		this.show = show;
		return this;
	}

	@XmlTransient
	public Status getStatus() {
		return status;
	}

	public Presence setStatus(Status status) {
		this.status = status;
		return this;
	}

	@XmlElements({ @XmlElement(name = "x", type = X.class) })
	public List<Field<?>> getFields() {
		return this.vCardFields.getFields();
	}

	public Presence clear() {
		super.clear();
		super.setType((String) null);
		this.show = null;
		this.status = null;
		return this;
	}

	@Override
	public Presence asShow(String show) {
		this.setShow(show != null ? new Show(show) : null);
		return this;
	}

	@Override
	public Presence asStatus(String status) {
		this.setStatus(new Status(status != null ? status : null));
		return this;
	}

	@Override
	public Presence asType(String type) {
		return this.setType(Type.parse(type));
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case X:
			this.vCardFields.add(X.class.cast(ob));
			break;
		case STATUS:
			this.setStatus((Status) ob);
			break;
		case SHOW:
			this.setShow((Show) ob);
			break;
		}
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.vCardFields.iterator();
	}

	@Override
	public Boolean isEmbed() {
		return this.vCardFields.isEmbed();
	}

	@Override
	public Fields add(Field<?> field) {
		return this.vCardFields.add(field);
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.vCardFields.findField(name, clazz);
	}
}
