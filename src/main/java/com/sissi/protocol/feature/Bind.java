package com.sissi.protocol.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;
import com.sissi.protocol.Required;

/**
 * @author Kim.shen 2013-10-20
 */
@XmlRootElement
public class Bind implements Feature {

	public final static Bind FEATURE = new Bind();

	public final static String NAME = "bind";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-bind";

	private Bind() {

	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	@XmlElement
	public Required getRequired() {
		return null;
	}
}
