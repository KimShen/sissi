package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = CredentialsExpired.NAME)
public class CredentialsExpired extends ServerErrorDetail {

	public final static CredentialsExpired DETAIL = new CredentialsExpired();

	public final static String NAME = "credentials-expired";

	private CredentialsExpired() {
		super(XMLNS_ELEMENT);
	}
}
