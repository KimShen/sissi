package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.DiscoInfo;

/**
 * @author kim 2014年3月12日
 */
public class DiscoInfoMucNodeMatcher extends ClassMatcher {

	private final String node = "x-roomuser-item";

	private final JIDBuilder jidBuilder;

	public DiscoInfoMucNodeMatcher(JIDBuilder jidBuilder) {
		super(DiscoInfo.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup() && protocol.cast(DiscoInfo.class).node(this.node);
	}
}
