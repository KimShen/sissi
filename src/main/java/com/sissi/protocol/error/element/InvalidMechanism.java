package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = InvalidMechanism.NAME)
public class InvalidMechanism implements ErrorDetail {

	public final static InvalidMechanism DETAIL = new InvalidMechanism();

	public final static String NAME = "invalid-mechanism";

	private InvalidMechanism() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}