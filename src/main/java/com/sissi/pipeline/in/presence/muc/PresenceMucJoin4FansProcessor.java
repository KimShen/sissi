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
 * 对当前JID推送其他房客出席消息
 * 
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin4FansProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final CodeStatusAdder judeger;

	public PresenceMucJoin4FansProcessor(RoomBuilder room, CodeStatusAdder judeger) {
		super();
		this.room = room;
		this.judeger = judeger;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		Room room = this.room.build(group);
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			MucRelation relation = each.cast(MucRelation.class);
			JID to = super.build(relation.jid());
			context.write(presence.clear().add(this.judeger.add(new XUser(group, context.jid(), room.allowed(context.jid(), RoomConfig.WHOISEXISTS)).item(new Item(room.allowed(context.jid(), RoomConfig.WHOISALLOW, to), relation))).cast(XUser.class)).clauses(super.findOne(to, true).status().clauses()).setFrom(group.resource(relation.name())));
		}
		return true;
	}
}
