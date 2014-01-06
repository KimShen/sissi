package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = TemporaryAuthFailure.NAME)
public class TemporaryAuthFailure implements ErrorDetail {

	public final static TemporaryAuthFailure DETAIL = new TemporaryAuthFailure();

	public final static String NAME = "temporary-auth-failure";

	public TemporaryAuthFailure() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
