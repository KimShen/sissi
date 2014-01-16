package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = AccountDisabled.NAME)
public class AccountDisabled extends ServerErrorDetail {

	public final static AccountDisabled DETAIL = new AccountDisabled();

	public final static String NAME = "account-disabled";

	private AccountDisabled() {
		super(XMLNS_ELEMENT);
	}
}
