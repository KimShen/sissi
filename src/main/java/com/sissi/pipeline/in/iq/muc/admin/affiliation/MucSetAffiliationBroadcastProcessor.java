package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBroadcast;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * 更新岗位广播
 * 
 * @author kim 2014年3月14日
 */
public class MucSetAffiliationBroadcastProcessor extends ProxyProcessor {

	private final MucRelationContext relationContext;

	private final AffiliationBroadcast broadcast;

	private final RoomBuilder room;

	public MucSetAffiliationBroadcastProcessor(MucRelationContext relationContext, AffiliationBroadcast broadcast, RoomBuilder room) {
		super();
		this.relationContext = relationContext;
		this.broadcast = broadcast;
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		Room room = this.room.build(group);
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			for (Relation each : relationContext.ourRelations(super.build(item.getJid()), group)) {
				this.broadcast.broadcast(item.setNick(each.name()).group(group), room, item, context);
			}
		}
		return true;
	}
}
