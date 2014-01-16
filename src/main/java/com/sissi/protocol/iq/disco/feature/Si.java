package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月13日
 */
public class Si extends DiscoFeature {

	public final static Si FEATURE = new Si();

	private final static String VAR = "http://jabber.org/protocol/si";

	private Si() {
		super(VAR);
	}
}
