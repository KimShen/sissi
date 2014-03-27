package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月24日
 */
public class MucOwnerSetCancelProcessor extends ProxyProcessor {

	private final Input proxy;

	public MucOwnerSetCancelProcessor(Input proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		//TODO 销毁房间
		return this.proxy.input(context, new Presence().setTo(super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(RelationMuc.class).jid()));
	}
}
