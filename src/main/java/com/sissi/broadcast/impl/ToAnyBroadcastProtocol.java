package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月8日
 */
abstract class ToAnyBroadcastProtocol {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private RelationContext relationContext;

	public void setJidBuilder(JIDBuilder jidBuilder) {
		this.jidBuilder = jidBuilder;
	}

	public void setAddressing(Addressing addressing) {
		this.addressing = addressing;
	}

	public void setRelationContext(RelationContext relationContext) {
		this.relationContext = relationContext;
	}

	protected JIDBuilder getJidBuilder() {
		return jidBuilder;
	}

	protected Addressing getAddressing() {
		return addressing;
	}

	protected RelationContext getRelationContext() {
		return relationContext;
	}
}
