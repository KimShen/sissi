package com.sissi.protocol;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.error.Error;

/**
 * @author kim 2013-10-24
 */
abstract public class Protocol implements Element {

	public static enum Type {

		SET, GET, RESULT, ERROR, CANCEL;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public Boolean equals(String type) {
			return this == Type.parse(type);
		}

		public static Type parse(String value) {
			return Type.valueOf(value.toUpperCase());
		}
	}

	private String id;

	private String from;

	private String to;

	private String type;

	private Error error;

	private Protocol parent;

	@XmlTransient
	public Protocol getParent() {
		return parent != null ? this.parent : this;
	}

	public Protocol setParent(Protocol parent) {
		this.parent = parent;
		return this;
	}

	public Boolean hasParent() {
		return this.parent != null;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public Protocol setId(String id) {
		this.id = id;
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return from;
	}

	public Protocol setFrom(String from) {
		this.from = from;
		return this;
	}

	public Protocol setFrom(JID from) {
		this.from = from.asString();
		return this;
	}

	@XmlAttribute
	public String getTo() {
		return to;
	}

	public Protocol setTo(String to) {
		this.to = to;
		return this;
	}

	public Protocol setTo(JID to) {
		this.to = to.asString();
		return this;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public Protocol setType(String type) {
		this.type = type;
		return this;
	}

	public Protocol setType(Type type) {
		this.type = type.toString();
		return this;
	}

	@XmlElement
	public Error getError() {
		return error;
	}

	public Protocol setError(Failed failed) {
		this.setType(Type.ERROR);
		this.error = new Error(failed);
		return this;
	}

	public Protocol reply() {
		this.exchange().setId(this.getId());
		return this;
	}

	private Protocol exchange() {
		final String tempFrom = this.getFrom();
		this.setFrom(this.getTo());
		this.setTo(tempFrom);
		return this;
	}

	public Protocol clear() {
		this.id = null;
		this.from = null;
		this.to = null;
		this.type = null;
		return this;
	}
}
