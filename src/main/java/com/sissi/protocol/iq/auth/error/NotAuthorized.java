package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2013年12月7日
 */
@XmlType(namespace = Auth.XMLNS)
@XmlRootElement(name = NotAuthorized.NAME)
public class NotAuthorized implements ErrorDetail {

	public final static NotAuthorized DETAIL = new NotAuthorized();

	public final static String NAME = "not-authorized";

	public NotAuthorized() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
