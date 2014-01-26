package com.sissi.ucenter.relation;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationInductor;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月22日
 */
public class ResearchRelationInductor implements RelationInductor {

	private final BroadcastProtocol broadcastProtocol;

	private RelationContext relationContext;

	public ResearchRelationInductor(BroadcastProtocol broadcastProtocol) {
		super();
		this.broadcastProtocol = broadcastProtocol;
	}

	public void setRelationContext(RelationContext relationContext) {
		this.relationContext = relationContext;
	}

	@Override
	public RelationInductor update(JID master, JID slave) {
		RelationRoster relation = RelationRoster.class.cast(this.relationContext.ourRelation(master, slave));
		if (relation.isActivate()) {
			this.broadcastProtocol.broadcast(master, new IQ().add(new Roster(new GroupItem(relation))).setType(ProtocolType.SET));
		}
		return this;
	}

	public RelationInductor remove(JID master, JID slave) {
		this.broadcastProtocol.broadcast(master, new IQ().add(new Roster(new GroupItem(RelationRoster.class.cast(this.relationContext.ourRelation(master, slave))).setSubscription(RosterSubscription.REMOVE.toString()))).setType(ProtocolType.SET));
		return this;
	}
}
