package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月13日
 */
public class Bytestreams extends DiscoFeature {

	public final static Bytestreams FEATURE = new Bytestreams();

	private final static String VAR = "http://jabber.org/protocol/bytestreams";

	private Bytestreams() {
		super(VAR);
	}
}
