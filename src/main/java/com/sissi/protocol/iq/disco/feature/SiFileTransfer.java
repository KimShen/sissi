package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月13日
 */
public class SiFileTransfer extends DiscoFeature {

	public final static SiFileTransfer FEATURE = new SiFileTransfer();

	private final static String VAR = "http://jabber.org/protocol/si/profile/file-transfer";

	private SiFileTransfer() {
		super(VAR);
	}
}
