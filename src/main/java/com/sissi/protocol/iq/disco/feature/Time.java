package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2014年2月10日
 */
public class Time extends DiscoFeature {

	public final static Time FEATURE = new Time();

	private final static String VAR = "urn:xmpp:time";

	private Time() {
		super(VAR);
	}
}
