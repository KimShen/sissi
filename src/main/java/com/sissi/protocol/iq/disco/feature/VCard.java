package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月5日
 */
public class VCard extends DiscoFeature {

	public final static VCard FEATURE = new VCard();

	private final static String VAR = "vcard-temp";

	private VCard() {
		super(VAR);
	}
}
