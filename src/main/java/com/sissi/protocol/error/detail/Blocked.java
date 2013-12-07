package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Failed.Detail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = "urn:xmpp:blocking:errors")
public class Blocked implements Detail {

	public final static Blocked DETAIL = new Blocked();

	private final static String XMLNS = "urn:xmpp:blocking:errors";

	private Blocked() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

}
