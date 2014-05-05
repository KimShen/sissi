package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 更新角色广播
 * 
 * @author kim 2014年3月14日
 */
public class MucSetRoleBroadcastProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final CodeStatusAdder judger;

	private final MucRelationMapping mapping;

	public MucSetRoleBroadcastProcessor(RoomBuilder room, CodeStatusAdder judger, MucRelationMapping mapping) {
		super();
		this.room = room;
		this.judger = judger;
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		Room room = this.room.build(group);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			// 同Nickname多JID
			for (JID each : this.mapping.mapping(item.actor(context.jid()).group(group))) {
				MucRelation relation = super.ourRelation(each, group).cast(MucRelation.class);
				for (JID to : super.whoSubscribedMe(group)) {
					super.findOne(to, true).write(item.presence().add(this.judger.add(new XUser(group, to, room.allowed(to, RoomConfig.WHOISEXISTS)).item(item.hidden(room.allowed(to, RoomConfig.WHOISALLOW, each)).relation(relation.cast(MucRelation.class).role(item.getRole(), true)).actor(context.jid()))).cast(XUser.class)));
				}
			}
		}
		return true;
	}
}
