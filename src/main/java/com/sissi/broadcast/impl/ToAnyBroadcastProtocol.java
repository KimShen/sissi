package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月8日
 */
abstract class ToAnyBroadcastProtocol {

	protected final JIDBuilder jidBuilder;

	protected final Addressing addressing;

	protected final RelationContext relationContext;

	public ToAnyBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}
}
