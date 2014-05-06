package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
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
 * 修改昵称
 * 
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2ReplaceProcessor extends ProxyProcessor {

	private final Input proxy;

	private final boolean resend;

	private final RoomBuilder room;

	private final CodeStatusAdder adder;

	public PresenceMucJoin2ReplaceProcessor(Input proxy, boolean resend, RoomBuilder room, CodeStatusAdder adder) {
		super();
		this.room = room;
		this.adder = adder;
		this.proxy = proxy;
		this.resend = resend;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		String nickname = group.resource();
		MucRelation relation = super.ourRelation(context.jid(), group).cast(MucRelation.class);
		// relation.activate(),如果已进入房间. group.resource().equals(relation.name()), 昵称未变
		return relation.activate() ? group.resource().equals(relation.name()) ? this.resend ? this.proxy.input(context, protocol) : false : this.writeAndReturn(context, group, nickname, relation) : true;
	}

	private boolean writeAndReturn(JIDContext context, JID group, String nickname, MucRelation relation) {
		Room room = this.room.build(group);
		// 通知其他房客昵称修改
		Presence presence = new Presence();
		for (Relation each : super.myRelations(group)) {
			JID to = super.build(each.cast(MucRelation.class).jid());
			super.findOne(to, true).write(presence.clear().add(this.adder.add(new XUser(group, to, room.allowed(to, RoomConfig.WHOISEXISTS)).item(new Item(room.allowed(to, RoomConfig.WHOISALLOW, context.jid()), nickname, relation))).cast(XUser.class)).type(PresenceType.UNAVAILABLE).setFrom(group.resource(relation.name())));
		}
		return false;
	}
}
