package com.sisi.protocol;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2013-10-24
 */
abstract public class Protocol {

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

	public void reply(Protocol protocol) {
		protocol.setId(this.getId());
		protocol.setFrom(this.getTo());
		protocol.setTo(this.getFrom());
	}
}
