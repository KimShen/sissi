package com.sissi.pipeline.in;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年4月16日
 */
public class MucMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MucMatcher(Class<? extends Protocol> clazz, JIDBuilder jidBuilder) {
		super(clazz);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup();
	}
}
