package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.codec.binary.Base64;

import com.sissi.protocol.Element;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Success implements Element {

	public final static Success INSTANCE = new Success();

	private byte[] rspauth;

	private Success() {

	}

	public Success(byte[] rspauth) {
		super();
		this.rspauth = rspauth;
	}

	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}

	@XmlValue
	public String getRspauth() {
		return Base64.encodeBase64String(this.rspauth);
	}

	@Override
	@XmlTransient
	public String getId() {
		return null;
	}

	@Override
	public Success setId(String id) {
		return this;
	}

	@Override
	@XmlTransient
	public String getFrom() {
		return null;
	}

	@Override
	public Success setFrom(String from) {
		return this;
	}

	@Override
	@XmlTransient
	public String getTo() {
		return null;
	}

	@Override
	public Success setTo(String to) {
		return this;
	}

	@Override
	@XmlTransient
	public String getType() {
		return null;
	}

	@Override
	public Success setType(String type) {
		return this;
	}
}
