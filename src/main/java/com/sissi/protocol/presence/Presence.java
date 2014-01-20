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
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013-10-28
 */
@MappingMetadata(uri = Presence.XMLNS, localName = Presence.NAME)
@XmlRootElement
public class Presence extends Protocol implements com.sissi.context.Status, Fields, Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "presence";

	private Delay delay;

	private BeanFields fields;

	private PresenceShow show;

	private PresenceStatus status;

	private PresencePriority priority;

	public Presence() {
		super();
		this.fields = new BeanFields(false);
	}

	public Presence(JID from, StatusClauses clauses) {
		this();
		this.copyFromClauses(clauses).setFrom(from);
	}

	private Presence copyFromClauses(StatusClauses clauses) {
		this.setShow(clauses.find(StatusClauses.KEY_SHOW)).setStatus(clauses.find(StatusClauses.KEY_STATUS)).setAvator(clauses.find(StatusClauses.KEY_AVATOR)).setType(clauses.find(StatusClauses.KEY_TYPE));
		return this;
	}

	private XVCard findXVard() {
		return XVCard.class.cast(this.fields != null ? this.fields.findField(XVCard.NAME, XVCard.class) : null);
	}

	@XmlTransient
	public Integer getPriority() {
		return this.priority != null ? Integer.parseInt(this.priority.getText()) : 0;
	}

	public Presence setType(PresenceType type) {
		super.setType(type.toString());
		return this;
	}

	public Presence setFrom(JID from) {
		super.setFrom(from.asString());
		return this;
	}

	public Presence setTo(JID to) {
		super.setTo(to);
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

	@XmlElement(name = PresenceShow.NAME)
	public String getShowAsText() {
		return this.show != null ? this.show.getText() : null;
	}

	@XmlElement(name = PresenceStatus.NAME)
	public String getStatusAsText() {
		return this.status != null ? this.status.getText() : null;
	}

	@XmlTransient
	public String getAvatorAsText() {
		XVCard x = this.findXVard();
		XVCardPhoto xp = x != null ? Fields.class.cast(x).findField(XVCardPhoto.NAME, XVCardPhoto.class) : null;
		return xp != null ? xp.getValue() : null;
	}

	public Presence setShow(PresenceShow show) {
		this.show = show;
		return this;
	}

	public Presence setShow(String show) {
		this.show = new PresenceShow(show);
		return this;
	}

	public Presence setStatus(PresenceStatus status) {
		this.status = status;
		return this;
	}

	public Presence setStatus(String status) {
		this.status = new PresenceStatus(status);
		return this;
	}

	@Override
	public Presence setStatus(StatusClauses clauses) {
		return this.copyFromClauses(clauses);
	}

	@Override
	public StatusClauses getStatusClauses() {
		return new PresenceClauses();
	}

	public Presence setAvator(String type) {
		this.add(new XVCard().add(new XVCardPhoto(type)));
		return this;
	}

	@XmlElements({ @XmlElement(name = XVCard.NAME, type = XVCard.class) })
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
		case PresenceStatus.NAME:
			this.setStatus((PresenceStatus) ob);
			return;
		case PresenceShow.NAME:
			this.setShow((PresenceShow) ob);
			return;
		case PresencePriority.NAME:
			this.priority = PresencePriority.class.cast(ob);
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
