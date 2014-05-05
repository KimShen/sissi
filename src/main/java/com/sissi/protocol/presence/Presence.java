package com.sissi.protocol.presence;

import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JID;
import com.sissi.context.StatusClauses;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.field.impl.BeanFields;
import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.muc.Destory;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.offline.Delay;

/**
 * @author kim 2013-10-28
 */
@Metadata(uri = Presence.XMLNS, localName = Presence.NAME)
@XmlRootElement
public class Presence extends Protocol implements com.sissi.context.Status, Fields, Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "presence";

	private final static XMuc muc = new XMuc();

	private final static BeanFields empty = new BeanFields(false);

	private final static Log log = LogFactory.getLog(Presence.class);

	private PresenceClauses presenceClauses = new PresenceClauses();

	private BeanFields fields;

	private Delay delay;

	private PresenceShow show;

	private PresenceStatus status;

	private PresencePriority priority;

	private boolean fields() {
		return this.fields != null;
	}

	private boolean status() {
		return PresenceType.parse(this.getType()).in(PresenceType.AVAILABLE, PresenceType.UNAVAILABLE);
	}

	private Presence show(String show) {
		this.show = new PresenceShow(show);
		return this;
	}

	private Presence avator(String type) {
		this.add(new XVCard().add(new XVCardPhoto(type)));
		return this;
	}

	private Presence priority(String priority) {
		try {
			this.priority = priority != null ? new PresencePriority(priority) : null;
		} catch (Exception e) {
			log.debug(e.toString());
			Trace.trace(log, e);
		}
		return this;
	}

	public boolean valid() {
		return PresenceType.parse(this.getType()) != PresenceType.NONE;
	}

	private XVCard findXVard() {
		return XVCard.class.cast(this.fields() ? this.fields.findField(XVCard.NAME, XVCard.class) : null);
	}

	public Presence type(PresenceType type) {
		super.setType(type.toString());
		if (!this.status()) {
			this.fields = null;
			this.priority = null;
			this.show = null;
			this.status = null;
		}
		return this;
	}

	public Presence destory(Destory destory) {
		return this.type(PresenceType.UNAVAILABLE).add(new XUser().destory(destory).item(new Item().setAffiliation(ItemAffiliation.NONE.toString()).setRole(ItemRole.NONE.toString())));
	}

	public Presence setFrom(JID from) {
		super.setFrom(from.asString());
		return this;
	}

	public Presence setTo(JID to) {
		super.setTo(to);
		return this;
	}

	public Presence delay(Delay delay) {
		this.delay = delay;
		return this;
	}

	@XmlElement
	public Delay getDelay() {
		return this.delay;
	}

	public int priority(int def) {
		return this.priority != null ? this.priority.priority(def) : def;
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
		XVCardPhoto xp = x != null ? x.findField(XVCardPhoto.NAME, XVCardPhoto.class) : null;
		return xp != null ? xp.getValue() : null;
	}

	public Presence show(PresenceShow show) {
		this.show = show;
		return this;
	}

	public Presence status(String status) {
		this.status = new PresenceStatus(status);
		return this;
	}

	public Presence status(PresenceStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public Presence clauses(StatusClauses clauses) {
		this.show(clauses.find(StatusClauses.KEY_SHOW)).status(clauses.find(StatusClauses.KEY_STATUS)).avator(clauses.find(StatusClauses.KEY_AVATOR)).priority(clauses.find(StatusClauses.KEY_PRIORITY)).setType(clauses.find(StatusClauses.KEY_TYPE));
		return this;
	}

	@Override
	public StatusClauses clauses() {
		return this.presenceClauses;
	}

	@XmlElements({ @XmlElement(name = XVCardPhoto.NAME, type = XVCardPhoto.class), @XmlElement(name = XVCard.NAME, type = XVCard.class), @XmlElement(name = XMuc.NAME, type = XMuc.class), @XmlElement(name = XUser.NAME, type = XUser.class) })
	public List<Field<?>> getFields() {
		return this.fields() ? this.fields.getFields() : empty.getFields();
	}

	public Presence reset() {
		if (this.fields()) {
			this.fields.reset();
		}
		return this;
	}

	public Presence clear() {
		super.clear();
		this.reset();
		this.show = null;
		this.status = null;
		return this;
	}

	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	@Override
	public Iterator<Field<?>> iterator() {
		return this.fields() ? this.fields.iterator() : empty.iterator();
	}

	@Override
	public boolean isEmbed() {
		return this.fields() ? this.fields.isEmbed() : empty.isEmbed();
	}

	public boolean isEmpty() {
		return this.fields() ? this.fields.isEmpty() : empty.isEmpty();
	}

	@Override
	public Presence add(Field<?> field) {
		if (!this.fields()) {
			this.fields = new BeanFields(false);
		}
		this.fields.add(field);
		return this;
	}

	public Presence clone() {
		return new Presence().clauses(this.clauses()).setFrom(this.getFrom()).setTo(this.getTo()).setType(this.getType()).cast(Presence.class);
	}

	public Fields findFields(String name) {
		return this.fields() ? this.fields.findFields(name) : empty.findFields(name);
	}

	@Override
	public <T extends Field<?>> T findField(String name, Class<T> clazz) {
		return this.fields() ? this.fields.findField(name, clazz) : empty.findField(name, clazz);
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case X.NAME:
			this.add(Field.class.cast(ob));
			return;
		case PresenceStatus.NAME:
			this.status((PresenceStatus) ob);
			return;
		case PresenceShow.NAME:
			this.show((PresenceShow) ob);
			return;
		case PresencePriority.NAME:
			this.priority = PresencePriority.class.cast(ob);
			return;
		}
	}

	public static Presence muc() {
		return new Presence().add(Presence.muc);
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
