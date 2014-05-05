package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.Protocol;

/**
 * 对JID指定资源进行XMPP节广播
 * 
 * @author kim 2014年1月15日
 */
public class ToSelfBroadcastProtocol extends BaseBroadcastProtocol implements BroadcastProtocol {

	public ToSelfBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing) {
		super(jidBuilder, addressing);
	}

	@Override
	public ToSelfBroadcastProtocol broadcast(JID jid, Protocol protocol) {
		super.addressing.findOne(jid, true).write(protocol);
		return this;
	}
}
