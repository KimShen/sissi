package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = ResourceConstraint.NAME)
public class ResourceConstraint implements ErrorDetail {

	public final static ResourceConstraint DETAIL = new ResourceConstraint();

	public final static String NAME = "resource-constraint";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-stanzas";

	private ResourceConstraint() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
