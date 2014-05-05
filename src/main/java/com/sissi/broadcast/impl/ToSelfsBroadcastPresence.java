package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.Status;

/**
 * 对JID所有资源进行出席广播
 * 
 * @author kim 2014年1月15日
 */
public class ToSelfsBroadcastPresence extends BaseBroadcastPresence {

	protected ToSelfsBroadcastPresence(JIDBuilder jidBuilder, Addressing addressing) {
		super(jidBuilder, addressing);
	}

	public ToSelfsBroadcastPresence broadcast(JID jid, JID from, Status status) {
		super.addressing.find(jid).write(super.presenceBuilder.build(from, status));
		return this;
	}
}
