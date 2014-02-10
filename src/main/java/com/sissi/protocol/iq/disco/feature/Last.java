package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2014年2月10日
 */
public class Last extends DiscoFeature {

	public final static Last FEATURE = new Last();

	private final static String VAR = "jabber:iq:last";

	private Last() {
		super(VAR);
	}
}
