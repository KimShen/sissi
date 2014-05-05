package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * 订阅关系校验,至少为TO或BOTH
 * 
 * @author kim 2014年1月26日
 */
public class CheckRelationProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final String[] relations = new String[] { RosterSubscription.TO.toString(), RosterSubscription.BOTH.toString() };

	private final boolean shortcut;

	/**
	 * @param shortcut 是否忽略订阅关系
	 */
	public CheckRelationProcessor(boolean shortcut) {
		super();
		this.shortcut = shortcut;
	}

	/*
	 * 1, shortcut 2, Loop 3, 存在指定订阅关系
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.shortcut || context.jid().like(protocol.parent().getTo()) || this.ourRelation(context, protocol) ? true : this.writeAndReturn(context, protocol);
	}

	/**
	 * 订阅关系校验,至少为TO或BOTH
	 * 
	 * @param context
	 * @param protocol
	 * @return
	 */
	protected boolean ourRelation(JIDContext context, Protocol protocol) {
		return super.ourRelation(context.jid(), super.build(protocol.parent().getTo())).cast(RosterRelation.class).in(this.relations);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
