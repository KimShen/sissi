package com.sissi.protocol.iq.login;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-25
 */
@XmlRootElement
public class Challenge implements Element {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	private final static Log LOG = LogFactory.getLog(Challenge.class);

	private final static Base64 base64 = new Base64();

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
		try {
			return base64.encodeToString(this.text);
		} catch (Exception e) {
			LOG.fatal(e);
			throw new RuntimeException(e);
		}
	}

	@XmlAttribute
	public String getId() {
		return null;
	}

	public Element setId(String id) {
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return null;
	}

	public Element setFrom(String from) {
		return this;
	}

	@XmlAttribute
	public String getTo() {
		return null;
	}

	@Override
	public Element setTo(String to) {
		return null;
	}

	@Override
	@XmlAttribute
	public String getType() {
		return null;
	}

	@Override
	public Element setType(String type) {
		return this;
	}
}
