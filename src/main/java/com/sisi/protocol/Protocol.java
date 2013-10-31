package com.sisi.protocol;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author kim 2013-10-24
 */
abstract public class Protocol {

	public static enum Type {
		SET, GET, RESULT, ERROR;

		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private String id;

	private String from;

	private String to;

	private String type;

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@XmlAttribute
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Protocol reply(Protocol protocol) {
		protocol.setId(this.getId());
		protocol.setFrom(this.getTo());
		protocol.setTo(this.getFrom());
		return protocol;
	}

	public Protocol clear() {
		this.id = null;
		this.from = null;
		this.to = null;
		this.type = null;
		return this;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
