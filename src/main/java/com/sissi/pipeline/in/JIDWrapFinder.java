package com.sissi.pipeline.in;

import com.sissi.context.JID.JIDBuilder;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.Input.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年11月30日
 */
public abstract class JIDWrapFinder implements InputFinder {

	private JIDBuilder jidBuilder;

	public JIDWrapFinder(JIDBuilder jidBuilder) {
		super();
		this.jidBuilder = jidBuilder;
	}

	@Override
	public Input find(Protocol protocol) {
		return this.doFind(protocol.setFrom(this.jidBuilder.build(protocol.getFrom())).setTo(this.jidBuilder.build(protocol.getTo())));
	}

	abstract public Input doFind(Protocol protocol);

}
