package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月24日
 */
public class MucOwnerSetDestoryProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public MucOwnerSetDestoryProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.mucConfigBuilder.build(super.build(protocol.parent().getTo())).destory();
		return true;
	}
}
