package com.sisi.protocol.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-27
 */
@XmlRootElement
public class Message extends Protocol {

	private String body;

	public Message() {
		super();
	}

	public Message(String body) {
		super();
		this.body = body;
	}

	@XmlElement
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
