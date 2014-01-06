package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = CredentialsExpired.NAME)
public class EncryptionRequired implements ErrorDetail {

	public final static EncryptionRequired DETAIL = new EncryptionRequired();

	public final static String NAME = "encryption-required";

	private EncryptionRequired() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
