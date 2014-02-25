package com.sissi.protocol;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.sissi.context.JID;
import com.sissi.protocol.error.ServerError;

/**
 * @author kim 2013-10-24
 */
abstract public class Protocol implements Element {

	private String id;

	private String from;

	private String to;

	private String type;

	private String lang;

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

	public boolean to() {
		return this.getTo() != null;
	}

	public boolean to(String... tos) {
		for (String to : tos) {
			if (to.equals(this.getTo())) {
				return true;
			}
		}
		return false;
	}

	public boolean to(Collection<String> tos) {
		return tos.contains(this.getTo());
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

	public boolean type(String type) {
		return type.equals(this.getType());
	}

	public boolean type(ProtocolType type) {
		return type.equals(this.getType());
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public Protocol setType(String type) {
		this.type = type;
		return this;
	}

	public Protocol setType(ProtocolType type) {
		this.type = type.toString();
		return this;
	}

	@XmlAttribute(name = "xml:lang")
	public String getLang() {
		return this.lang;
	}

	public Protocol setLang(String lang) {
		this.lang = lang;
		return this;
	}

	public ServerError getError() {
		return this.error;
	}

	public Protocol setError(Error error) {
		this.setType(ProtocolType.ERROR);
		this.error = ServerError.class.cast(error);
		return this;
	}

	public Protocol reply() {
		final String from = this.getFrom();
		return this.setFrom(this.getTo()).setTo(from);
	}

	public Protocol clear() {
		this.id = null;
		this.type = null;
		return this;
	}

	public boolean clazz(Class<? extends Protocol> clazz) {
		return this.getClass() == clazz;
	}

	public boolean clazz(Collection<Class<? extends Protocol>> clazz) {
		for (Class<? extends Protocol> each : clazz) {
			if (this.getClass() == each) {
				return true;
			}
		}
		return false;
	}

	public <T extends Protocol> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
