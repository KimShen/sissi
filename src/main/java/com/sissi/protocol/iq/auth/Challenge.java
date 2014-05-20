package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.codec.binary.Base64;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-25
 */
@XmlRootElement
public class Challenge implements Element {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	private byte[] text;

	public Challenge() {
		super();
	}

	public Challenge(byte[] text) {
		this.text = text;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlValue
	public String getChallenge() {
		return Base64.encodeBase64String(this.text);
	}

	@XmlTransient
	public String getId() {
		return null;
	}

	public Challenge setId(String id) {
		return this;
	}

	@XmlTransient
	public String getFrom() {
		return null;
	}

	public String getFrom(boolean snapshot) {
		return null;
	}

	public Challenge setFrom(String from) {
		return this;
	}

	@XmlTransient
	public String getTo() {
		return null;
	}

	@Override
	public Challenge setTo(String to) {
		return this;
	}

	@Override
	@XmlTransient
	public String getType() {
		return null;
	}

	@Override
	public Challenge setType(String type) {
		return this;
	}
}
