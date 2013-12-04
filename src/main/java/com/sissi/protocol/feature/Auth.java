package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Stream;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Auth extends Feature {

	public final static Auth AUTH = new Auth();

	private final static String XMLNS = "http://jabber.org/features/iq-auth";

	private Auth() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
