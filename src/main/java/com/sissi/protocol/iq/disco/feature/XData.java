package com.sissi.protocol.iq.disco.feature;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * XEP 0004
 * @author kim 2013年12月5日
 */
public class XData extends DiscoFeature {

	public final static XData FEATURE = new XData();

	private final static String VAR = "jabber:x:data";

	private XData() {
		super(VAR);
	}
}
