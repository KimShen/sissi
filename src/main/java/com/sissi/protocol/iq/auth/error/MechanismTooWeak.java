package com.sissi.protocol.iq.auth.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = MechanismTooWeak.NAME)
public class MechanismTooWeak implements ErrorDetail {

	public final static MechanismTooWeak DETAIL = new MechanismTooWeak();

	public final static String NAME = "mechanism-too-weak";

	private MechanismTooWeak() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
