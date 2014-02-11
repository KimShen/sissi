package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public PresenceMucMatcher(JIDBuilder jidBuilder) {
		super(Presence.class);
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.getTo()).isGroup();
	}
}
