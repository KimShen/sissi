package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlRootElement
public class Bind implements Feature {

	public final static Bind FEATURE = new Bind();

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-bind";

	private Bind() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
