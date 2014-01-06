package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = IncorrectEncoding.NAME)
public class IncorrectEncoding implements ErrorDetail {

	public final static IncorrectEncoding DETAIL = new IncorrectEncoding();

	public final static String NAME = "incorrect-encoding";

	private IncorrectEncoding() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
