package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = AccountDisabled.NAME)
public class AccountDisabled implements ErrorDetail {

	public final static AccountDisabled DETAIL = new AccountDisabled();

	public final static String NAME = "account-disabled";

	private AccountDisabled() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
