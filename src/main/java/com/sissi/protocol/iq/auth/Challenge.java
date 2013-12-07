package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.protocol.Element;
import com.sissi.protocol.Failed;

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
			if (LOG.isFatalEnabled()) {
				LOG.fatal(e);
				e.printStackTrace();
			}
			return null;
		}
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

	@Override
	@XmlTransient
	public Failed getError() {
		return null;
	}

	@Override
	public Element setError(Failed failed) {
		return this;
	}
}
