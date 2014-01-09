package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年1月8日
 */
class ToAnyProtocolQueue {

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

	public JIDBuilder getJidBuilder() {
		return jidBuilder;
	}

	public Addressing getAddressing() {
		return addressing;
	}

	public RelationContext getRelationContext() {
		return relationContext;
	}
}
