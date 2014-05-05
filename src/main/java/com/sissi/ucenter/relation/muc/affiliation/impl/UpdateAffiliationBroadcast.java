package com.sissi.ucenter.relation.muc.affiliation.impl;

import com.sissi.addressing.Addressing;
import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.protocol.muc.XUser;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucItem;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.affiliation.AffiliationBroadcast;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * MUC岗位变更广播
 * 
 * @author kim 2014年3月24日
 */
public class UpdateAffiliationBroadcast implements AffiliationBroadcast {

	private final Addressing addressing;

	private final JIDBuilder jidBuilder;

	private final CodeStatusAdder adder;

	private final MucRelationContext relationContext;

	public UpdateAffiliationBroadcast(Addressing addressing, JIDBuilder jidBuilder, CodeStatusAdder adder, MucRelationContext relationContext) {
		super();
		this.adder = adder;
		this.addressing = addressing;
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
	}

	@Override
	public AffiliationBroadcast broadcast(JID group, Room room, MucItem item, JIDContext invoker) {
		JID jid = this.jidBuilder.build(item.getJid());
		// 向MUC房客广播MUC JID所有资源岗位变更
		for (Relation relation : this.relationContext.ourRelations(jid, group)) {
			for (JID to : this.relationContext.whoSubscribedMe(group)) {
				this.addressing.findOne(to, true).write(item.presence(room.pull(Dictionary.FIELD_AFFILIATION, String.class)).add(this.adder.add(new XUser(group, to, room.allowed(to, RoomConfig.WHOISEXISTS, null)).item(item.hidden(room.allowed(to, RoomConfig.WHOISALLOW, jid)).relation(relation.cast(MucRelation.class).affiliation(item.getAffiliation(), true)))).cast(XUser.class)));
			}
		}
		return this;
	}
}
