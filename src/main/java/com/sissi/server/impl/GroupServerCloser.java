package com.sissi.server.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.server.ServerCloser;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2013-11-20
 */
public class GroupServerCloser implements ServerCloser {

	private final RelationContext relationContext;

	public GroupServerCloser(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	@Override
	public GroupServerCloser close(JIDContext context) {
		for (JID group : this.relationContext.iSubscribedWho(context.jid())) {
			this.relationContext.remove(context.jid(), group);
		}
		return this;
	}
}
