package com.sissi.pipeline.in.iq.muc.admin.role;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 角色列表
 * 
 * @author kim 2014年3月14日
 */
public class MucGetRoleProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final MucRelationContext relationContext;

	public MucGetRoleProcessor(RoomBuilder room, MucRelationContext relationContext) {
		super();
		this.room = room;
		this.relationContext = relationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMucAdmin admin = protocol.cast(XMucAdmin.class);
		JID group = super.build(admin.clear().parent().getTo());
		Room room = this.room.build(group);
		for (Relation relation : this.relationContext.myRelations(group, admin.role())) {
			// 根据房间配置 可选的JID
			admin.add(new Item(room.allowed(context.jid(), RoomConfig.WHOISALLOW, group.resource(relation.name())), relation.cast(MucRelation.class)));
		}
		context.write(protocol.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
