package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年3月12日
 */
public class DiscoActionMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final boolean bare;

	public DiscoActionMatcher(Class<? extends Protocol> clazz, JIDBuilder jidBuilder, boolean bare) {
		super(clazz);
		this.jidBuilder = jidBuilder;
		this.bare = bare;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean support(JID jid) {
		return jid.isGroup() && jid.isBare() == this.bare;
	}
}