package com.sissi.protocol.presence;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.context.StatusClauses;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.offline.Delay;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanFields;

/**
 * @author kim 2013-10-28
 */
@Metadata(uri = Presence.XMLNS, localName = Presence.NAME)
@XmlRootElement
public class Presence extends Protocol implements com.sissi.context.Status, Fields, Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "presence";

	private final static Log log = LogFactory.getLog(Presence.class);

	private Delay delay;

	private BeanFields fields;

	private PresenceShow show;

	private PresenceStatus status;

	private PresencePriority priority;

	public Presence() {
		super();
		this.fields = new BeanFields(false);
	}

	private boolean status() {
		return PresenceType.parse(this.getType()).in(PresenceType.AVAILABLE, PresenceType.UNAVAILABLE);
	}

	private XVCard findXVard() {
		return XVCard.class.cast(this.fields != null ? this.fields.findField(XVCard.NAME, XVCard.class) : null);
	}

	public boolean type() {
		return PresenceType.parse(this.getType()) != null;
	}

	public boolean type(PresenceType type) {
		return type.equals(this.getType());
	}

	public Presence setType(PresenceType type) {
		super.setType(type.toString());
		if (!this.status()) {
			this.fields = null;
			this.priority = null;
			this.show = null;
			this.status = null;
		}
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

	public int priority(int def) {
		return this.priority != null ? this.priority.priority(def) : def;
	}

	private Presence setPriority(String priority) {
		try {
			this.priority = priority != null ? new PresencePriority(String.valueOf(priority)) : null;
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug(e.toString());
				e.printStackTrace();
			}
		}
		return this;
	}

	@XmlElement
	public Integer getPriority() {
		return this.priority != null ? this.priority.priority() : null;
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
	public Presence clauses(StatusClauses clauses) {
		this.setShow(clauses.find(StatusClauses.KEY_SHOW)).setStatus(clauses.find(StatusClauses.KEY_STATUS)).setAvator(clauses.find(StatusClauses.KEY_AVATOR)).setPriority(clauses.find(StatusClauses.KEY_PRIORITY)).setType(clauses.find(StatusClauses.KEY_TYPE));
		return this;
	}

	@Override
	public StatusClauses clauses() {
		return new PresenceClauses();
	}

	private Presence setAvator(String type) {
		this.add(new XVCard().add(new XVCardPhoto(type)));
		return this;
	}

	@XmlElements({ @XmlElement(name = XVCardPhoto.NAME, type = XVCardPhoto.class), @XmlElement(name = XVCard.NAME, type = XVCard.class), @XmlElement(name = XMuc.NAME, type = XMuc.class), @XmlElement(name = XUser.NAME, type = XUser.class) })
	public List<Field<?>> getFields() {
		return this.fields != null ? this.fields.getFields() : null;
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
	public boolean isEmbed() {
		return this.fields.isEmbed();
	}

	public boolean isEmpty() {
		return this.fields.isEmpty();
	}

	@Override
	public Presence add(Field<?> field) {
		if (this.fields == null) {
			this.fields = new BeanFields(false);
		}
		this.fields.add(field);
		return this;
	}

	public Fields findFields(String name) {
		return this.fields.findFields(name);
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
			case StatusClauses.KEY_PRIORITY:
				return String.valueOf(Presence.this.getPriority());
			}
			return null;
		}
	}
}
