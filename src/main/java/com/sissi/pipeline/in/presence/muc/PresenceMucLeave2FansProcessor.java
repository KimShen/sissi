package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 向其他房客推送当前JID离席消息
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2FansProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final CodeStatusAdder judger;

	public PresenceMucLeave2FansProcessor(RoomBuilder room, CodeStatusAdder judger) {
		super();
		this.room = room;
		this.judger = judger;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		MucRelation relation = super.ourRelation(context.jid(), group).cast(MucRelation.class).noneRole();
		Room room = this.room.build(group);
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.cast(MucRelation.class).jid());
			super.findOne(to, true).write(presence.clear().add(this.judger.add(new XUser(group, to, room.allowed(to, RoomConfig.WHOISEXISTS)).item(new Item(room.allowed(to, RoomConfig.WHOISALLOW, context.jid()), relation))).cast(XUser.class)).type(PresenceType.UNAVAILABLE).setFrom(protocol.getTo()));
		}
		return true;
	}
}
