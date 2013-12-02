package com.sissi.protocol.iq.login;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.pipeline.in.auth.impl.DigestAuthCallback;
import com.sissi.pipeline.in.auth.impl.PlainAuthCallback;
import com.sissi.protocol.Feature;
import com.sissi.protocol.Stream;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Mechanisms extends Feature {
	
	public final static Mechanisms MECHANISMS = new Mechanisms(DigestAuthCallback.MECHANISM, PlainAuthCallback.MECHANISM);

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
	
	public void setMechanism(Set<String> mechanism) {
		this.mechanism = mechanism;
	}

	public Set<String> getMechanism() {
		return mechanism;
	}
}
