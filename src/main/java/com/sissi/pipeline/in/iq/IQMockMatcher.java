package com.sissi.pipeline.in.iq;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月24日
 */
public class IQMockMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final Addressing addressing;

	public IQMockMatcher(Class<? extends Protocol> clazz, JIDBuilder jidBuilder, Addressing addressing) {
		super(clazz);
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.addressing.resources(this.jidBuilder.build(protocol.getParent().getTo())).isEmpty();
	}
}
