package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月5日
 */
public class Blocking extends DiscoFeature {

	public final static Blocking FEATURE = new Blocking();

	private final static String VAR = "urn:xmpp:blocking";

	private Blocking() {
		super(VAR);
	}
}
