package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Feature;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Session extends Feature {

	public static final Session FEATURE = new Session();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-session";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
