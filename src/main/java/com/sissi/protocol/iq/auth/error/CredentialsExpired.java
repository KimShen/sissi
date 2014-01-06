package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = CredentialsExpired.NAME)
public class CredentialsExpired implements ErrorDetail {

	public final static CredentialsExpired DETAIL = new CredentialsExpired();

	public final static String NAME = "credentials-expired";

	private CredentialsExpired() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
