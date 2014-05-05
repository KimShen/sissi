package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.DiscoInfo;

/**
 * <query xmlns="http://jabber.org/protocol/disco#info" node="x-roomuser-item"/>
 * 
 * @author kim 2014年3月12日
 */
public class DiscoInfoNicknameMatcher extends ClassMatcher {

	private final String node = "x-roomuser-item";

	public DiscoInfoNicknameMatcher() {
		super(DiscoInfo.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(DiscoInfo.class).node(this.node);
	}
}
