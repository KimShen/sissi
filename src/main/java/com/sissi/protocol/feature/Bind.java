package com.sissi.protocol.feature;

import com.sissi.protocol.Feature;

/**
 * @author Kim.shen 2013-10-20
 */
public class Bind extends Feature {

	public final static Bind FEATURE = new Bind();

	public final static String NAME = "bind";

	private final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-bind";

	private Bind() {
		super(XMLNS);
	}
}
