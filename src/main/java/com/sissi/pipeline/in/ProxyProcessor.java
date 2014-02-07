package com.sissi.pipeline.in;

import java.util.Set;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.BroadcastPresence;
import com.sissi.broadcast.BroadcastProtocol;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;
import com.sissi.context.Status;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2013-11-16
 */
abstract public class ProxyProcessor implements Input {

	protected JIDBuilder jidBuilder;

	protected Addressing addressing;

	protected BroadcastPresence presenceQueue;

	protected BroadcastProtocol protocolQueue;

	protected RelationContext relationContext;

	public void setAddressing(Addressing addressing) {
		this.addressing = addressing;
	}

	public void setJidBuilder(JIDBuilder jidBuilder) {
		this.jidBuilder = jidBuilder;
	}

	public void setRelationContext(RelationContext relationContext) {
		this.relationContext = relationContext;
	}

	public void setPresenceQueue(BroadcastPresence presenceQueue) {
		this.presenceQueue = presenceQueue;
	}

	public void setProtocolQueue(BroadcastProtocol protocolQueue) {
		this.protocolQueue = protocolQueue;
	}

	protected JID build(String jid) {
		return this.jidBuilder.build(jid);
	}

	protected JID build(String username, String resource) {
		return this.jidBuilder.build(username, resource);
	}

	protected ProxyProcessor join(JIDContext context) {
		this.addressing.join(context);
		return this;
	}

	protected JIDContext find(JID jid) {
		return this.addressing.find(jid);
	}

	protected JIDContext findOne(JID jid) {
		return this.addressing.findOne(jid);
	}

	protected JIDContext findOne(JID jid, Boolean usingResource) {
		return this.addressing.findOne(jid, usingResource);
	}

	public ProxyProcessor priority(JIDContext context) {
		this.addressing.priority(context);
		return this;
	}

	public JIDs resources(JID jid) {
		return this.addressing.resources(jid);
	}

	public JIDs resources(JID jid, Boolean usingResource) {
		return this.addressing.resources(jid, usingResource);
	}

	protected ProxyProcessor broadcast(JID jid, JID from, Status status) {
		this.presenceQueue.broadcast(jid, from, status);
		return this;
	}

	protected ProxyProcessor broadcast(JID jid, Protocol protocol) {
		this.protocolQueue.broadcast(jid, protocol);
		return this;
	}

	protected ProxyProcessor establish(JID from, Relation relation) {
		this.relationContext.establish(from, relation);
		return this;
	}

	protected ProxyProcessor update(JID from, JID to, String state) {
		this.relationContext.update(from, to, state);
		return this;
	}

	protected ProxyProcessor remove(JID from, JID to) {
		this.relationContext.remove(from, to);
		return this;
	}

	protected Relation ourRelation(JID from, JID to) {
		return this.relationContext.ourRelation(from, to);
	}

	protected Set<Relation> myRelations(JID from) {
		return this.relationContext.myRelations(from);
	}

	protected Set<JID> whoSubscribedMe(JID from) {
		return this.relationContext.whoSubscribedMe(from);
	}

	protected Set<JID> iSubscribedWho(JID from) {
		return this.relationContext.iSubscribedWho(from);
	}
}