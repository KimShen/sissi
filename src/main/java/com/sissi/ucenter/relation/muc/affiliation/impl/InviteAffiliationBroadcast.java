package com.sissi.ucenter.relation.muc.affiliation.impl;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.Invite;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBroadcast;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * MUC房间邀请广播
 * 
 * @author kim 2014年3月24日
 */
public class InviteAffiliationBroadcast implements AffiliationBroadcast {

	private final Input proxy;

	private final JIDBuilder jidBuilder;

	public InviteAffiliationBroadcast(Input proxy, JIDBuilder jidBuilder) {
		super();
		this.proxy = proxy;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public AffiliationBroadcast broadcast(JID group, Room room, MucItem item, JIDContext invoker) {
		// 岗位限制则发出加入邀请
		if (!room.allowed(this.jidBuilder.build(item.getJid()), RoomConfig.AFFILIATIONALLOW)) {
			// Message.to = group, Invite.to = item.jid
			this.proxy.input(invoker, new Message().muc(new XUser().invite(new Invite().setTo(item.getJid()))).setTo(group));
		}
		return this;
	}
}
