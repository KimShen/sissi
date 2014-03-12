package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.pipeline.in.iq.ToProxyMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.DiscoInfo;

/**
 * @author kim 2014年3月12日
 */
public class DiscoInfoMucNodeMatcher extends ToProxyMatcher {

	private final String node;

	public DiscoInfoMucNodeMatcher(String jid, String node) {
		super(DiscoInfo.class, jid);
		this.node = node;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(DiscoInfo.class).node(this.node);
	}
}