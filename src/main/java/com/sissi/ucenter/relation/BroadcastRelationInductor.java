package com.sissi.ucenter.relation;

import java.util.UUID;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.RelationInductor;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月22日
 */
public class BroadcastRelationInductor implements RelationInductor {

	private final BroadcastProtocol broadcastProtocol;

	private final Group remove;

	private RelationContext relationContext;

	public BroadcastRelationInductor(BroadcastProtocol broadcastProtocol) {
		this(broadcastProtocol, null);
	}

	public BroadcastRelationInductor(BroadcastProtocol broadcastProtocol, Group remove) {
		super();
		this.broadcastProtocol = broadcastProtocol;
		this.remove = remove;
	}

	public void setRelationContext(RelationContext relationContext) {
		this.relationContext = relationContext;
	}

	@Override
	public RelationInductor update(JID master, JID slave) {
		RelationRoster relation = RelationRoster.class.cast(this.relationContext.ourRelation(master, slave));
		if (relation.isActivate()) {
			this.broadcastProtocol.broadcast(master, new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(relation))).setType(ProtocolType.SET));
		}
		return this;
	}

	public RelationInductor remove(JID master, JID slave) {
		this.broadcastProtocol.broadcast(master, new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(slave).addOnEmpty(this.remove).setSubscription(RosterSubscription.REMOVE))).setType(ProtocolType.SET));
		return this;
	}
}
