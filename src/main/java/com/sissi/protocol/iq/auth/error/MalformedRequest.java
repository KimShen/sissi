package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = MalformedRequest.NAME)
public class MalformedRequest implements ErrorDetail {

	public final static MalformedRequest DETAIL = new MalformedRequest();

	public final static String NAME = "malformed-request";

	private MalformedRequest() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
