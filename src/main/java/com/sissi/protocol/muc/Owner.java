package com.sissi.protocol.muc;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月24日
 */
@Metadata(uri = Owner.XMLNS, localName = Owner.NAME)
public class Owner extends Protocol implements Collector {

	public final static String NAME = "query";

	public final static String XMLNS = "http://jabber.org/protocol/muc#owner";

	private XData data;

	public XData data() {
		return this.data;
	}

	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.data = XData.class.cast(ob);
	}
}
