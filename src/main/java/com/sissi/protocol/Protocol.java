package com.sissi.protocol;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.error.ServerError;

/**
 * @author kim 2013-10-24
 */
abstract public class Protocol implements Element {

	public static enum Type {

		SET, GET, RESULT, ERROR, CANCEL, WAIT;

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

	private Protocol parent;

	private ServerError error;

	@XmlTransient
	public Protocol getParent() {
		return this.parent != null ? this.parent : this;
	}

	public Protocol setParent(Protocol parent) {
		this.parent = parent;
		return this;
	}

	@XmlAttribute
	public String getId() {
		return this.id;
	}

	public Protocol setId(String id) {
		this.id = id;
		return this;
	}
	
	public Protocol setId(Long id) {
		this.id = String.valueOf(id);
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return this.from;
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
		return this.to;
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
		return this.type;
	}

	public Protocol setType(String type) {
		this.type = type;
		return this;
	}

	public Protocol setType(Type type) {
		this.type = type.toString();
		return this;
	}

	public ServerError getError() {
		return this.error;
	}

	public Protocol setError(Error error) {
		this.setType(Type.ERROR);
		this.error = ServerError.class.cast(error);
		return this;
	}

	public Protocol reply() {
		final String tempFrom = this.getFrom();
		this.setFrom(this.getTo());
		this.setTo(tempFrom);
		return this;
	}

	public Protocol clear() {
		this.id = null;
		this.type = null;
		return this;
	}
}
