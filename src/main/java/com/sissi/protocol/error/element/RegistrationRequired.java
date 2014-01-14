package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RegistrationRequired.NAME)
public class RegistrationRequired extends ElementErrorDetail {

	public final static RegistrationRequired DETAIL = new RegistrationRequired();

	public final static String NAME = "registration-required";

	private RegistrationRequired() {

	}
}
