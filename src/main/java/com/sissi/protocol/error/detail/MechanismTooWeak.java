package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = MechanismTooWeak.NAME)
public class MechanismTooWeak extends ServerErrorDetail {

	public final static MechanismTooWeak DETAIL = new MechanismTooWeak();

	public final static String NAME = "mechanism-too-weak";

	private MechanismTooWeak() {
		super(XMLNS_ELEMENT);
	}
}
