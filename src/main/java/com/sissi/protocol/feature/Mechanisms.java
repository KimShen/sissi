package com.sissi.protocol.feature;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.sissi.pipeline.in.auth.impl.DigestAuthCallback;
import com.sissi.pipeline.in.auth.impl.PlainAuthCallback;
import com.sissi.protocol.Feature;

/**
 * @author Kim.shen 2013-10-19
 */
public class Mechanisms extends Feature {

	public final static Mechanisms FEATURE = new Mechanisms(DigestAuthCallback.MECHANISM, PlainAuthCallback.MECHANISM);

	public final static String NAME = "mechanisms";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-sasl";

	private Set<String> mechanism;

	private Mechanisms() {
		super(XMLNS);
	}

	private Mechanisms(String... mechanisms) {
		super(XMLNS);
		this.mechanism = new HashSet<String>();
		for (String each : mechanisms) {
			this.mechanism.add(each);
		}
	}

	@Override
	@XmlElement
	public Required getRequired() {
		return Required.REQUIRED;
	}

	@XmlElements({ @XmlElement(name = "mechanism", type = String.class) })
	public Set<String> getMechanism() {
		return mechanism;
	}
}
