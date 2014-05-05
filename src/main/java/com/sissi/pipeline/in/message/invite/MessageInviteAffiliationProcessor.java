package com.sissi.pipeline.in.message.invite;

import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBuilder;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * Invite自动分配岗位(房间配置)
 * 
 * @author kim 2014年3月9日
 */
public class MessageInviteAffiliationProcessor extends ProxyProcessor {

	private final AffiliationBuilder affiliation;

	private final RoomBuilder room;

	public MessageInviteAffiliationProcessor(AffiliationBuilder affiliation, RoomBuilder room) {
		super();
		this.affiliation = affiliation;
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Room room = this.room.build(group);
		return room.allowed(context.jid(), RoomConfig.AFFILIATIONEXISTS) ? this.writeAndReturn(super.build(protocol.cast(Message.class).getMuc().getInvite().getTo()), group, room) : true;
	}

	private boolean writeAndReturn(JID jid, JID group, Room room) {
		this.affiliation.build(group).update(jid, room.pull(Dictionary.FIELD_AFFILIATION, String.class));
		return true;
	}
}
