package com.sissi.ucenter.relation.roster.impl;

import java.util.UUID;

import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.roster.RelationCascade;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * 级联广播
 * 
 * @author kim 2014年1月22日
 */
public class BroadcastRelationCascade implements RelationCascade {

	private final BroadcastProtocol broadcastProtocol;

	private final MongoOurRelation ourRelation;

	private final Group remove;

	/**
	 * @param broadcastProtocol
	 * @param ourRelation
	 * @param remove 默认Group(默认)
	 */
	public BroadcastRelationCascade(BroadcastProtocol broadcastProtocol, MongoOurRelation ourRelation, Group remove) {
		super();
		this.broadcastProtocol = broadcastProtocol;
		this.ourRelation = ourRelation;
		this.remove = remove;
	}

	@Override
	public RelationCascade update(JID master, JID slave) {
		RosterRelation relation = this.ourRelation.ourRelation(master, slave).cast(RosterRelation.class);
		// 激活则广播
		if (relation.activate()) {
			this.broadcastProtocol.broadcast(master, new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(relation))).setType(ProtocolType.SET));
		}
		return this;
	}

	public RelationCascade remove(JID master, JID slave) {
		this.broadcastProtocol.broadcast(master, new IQ().setId(UUID.randomUUID().toString()).add(new Roster(new GroupItem(slave).addOnEmpty(this.remove).setSubscription(RosterSubscription.REMOVE))).setType(ProtocolType.SET));
		return this;
	}
}
