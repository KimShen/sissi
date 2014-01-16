package com.sissi.protocol.feature;

import com.sissi.protocol.Feature;

/**
 * @author kim 2013年12月17日
 */
public class Starttls extends Feature {

	public final static Starttls FEATURE = new Starttls();

	public final static String NAME = "starttls";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-tls";

	private Starttls() {
		super(XMLNS);
	}
}
