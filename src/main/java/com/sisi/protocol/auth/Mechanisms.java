package com.sisi.protocol.auth;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sisi.protocol.Feature;
import com.sisi.protocol.core.Stream;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Mechanisms extends Feature {

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	private Set<String> mechanism;

	public Mechanisms() {
		super();
	}

	public Mechanisms(String... mechanisms) {
		super();
		this.mechanism = new HashSet<String>();
		for (String each : mechanisms) {
			this.mechanism.add(each);
		}
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public void addMechanism(String mechanism) {
		if (this.mechanism == null) {
			this.mechanism = new HashSet<String>();
		}
		this.mechanism.add(mechanism);
	}

	public Set<String> getMechanism() {
		return mechanism;
	}

	public void setMechanism(Set<String> mechanism) {
		this.mechanism = mechanism;
	}
}
