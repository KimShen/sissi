package com.sissi.protocol.feature;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.sissi.protocol.Feature;

/**
 * @author Kim.shen 2013-10-19
 */
public class Mechanisms extends Feature {

	public final static String NAME = "mechanisms";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	private final Set<String> mechanism = new HashSet<String>();

	public Mechanisms() {
		super(XMLNS);
	}

	public Mechanisms add(String mechanism) {
		this.mechanism.add(mechanism);
		return this;
	}

	@XmlElements({ @XmlElement(name = "mechanism", type = String.class) })
	public Set<String> getMechanism() {
		return this.mechanism;
	}
}
