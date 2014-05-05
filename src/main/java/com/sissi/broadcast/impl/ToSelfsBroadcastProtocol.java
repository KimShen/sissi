package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;

/**
 * 对JID所有资源进行XMPP节广播
 * 
 * @author kim 2014年1月15日
 */
public class ToSelfsBroadcastProtocol extends BaseBroadcastProtocol implements BroadcastProtocol {

	public ToSelfsBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing) {
		super(jidBuilder, addressing);
	}

	@Override
	public ToSelfsBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		super.addressing.find(jid).write(protocol);
		return this;
	}
}
