package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XUser;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 向其他房客推送当前JID出席消息
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2FansProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final CodeStatusAdder judger;

	public PresenceMucJoin2FansProcessor(RoomBuilder room, CodeStatusAdder judger) {
		super();
		this.room = room;
		this.judger = judger;
	}

	/*
	 * 房间如未配置则不通知其他房客
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Room room = this.room.build(group);
		if (!room.allowed(context.jid(), RoomConfig.CONFIGED)) {
			return true;
		}
		Presence presence = new Presence();
		MucRelation relation = super.ourRelation(context.jid(), group).cast(MucRelation.class);
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.jid());
			super.findOne(to, true).write(presence.clear().clauses(context.status().clauses()).add(this.judger.add(new XUser(group, to, room.allowed(to, RoomConfig.WHOISEXISTS)).item(new Item(room.allowed(to, RoomConfig.WHOISALLOW, context.jid()), relation))).cast(XUser.class)).setFrom(protocol.getTo()));
		}
		return true;
	}
}
