package com.sissi.protocol.feature;

import com.sissi.protocol.Feature;

/**
 * @author Kim.shen 2013-10-20
 */
public class Session extends Feature {

	public final static Session FEATURE = new Session();

	public final static String NAME = "session";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-session";

	public Session() {
		super(XMLNS);
	}
}
