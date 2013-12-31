package com.sissi.pipeline.in;

import java.util.Set;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceBroadcast;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
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

	protected PresenceBroadcast presenceQueue;

	protected ProtocolBraodcast protocolQueue;

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

	public void setPresenceQueue(PresenceBroadcast presenceQueue) {
		this.presenceQueue = presenceQueue;
	}

	public void setProtocolQueue(ProtocolBraodcast protocolQueue) {
		this.protocolQueue = protocolQueue;
	}

	public JID build(String jid) {
		return this.jidBuilder.build(jid);
	}

	public JID build(String username, String resource) {
		return this.jidBuilder.build(username, resource);
	}

	public ProxyProcessor leave(JIDContext context) {
		this.addressing.leave(context.getJid());
		return this;
	}

	public ProxyProcessor join(JIDContext context) {
		this.addressing.join(context);
		return this;
	}

	public JIDContext find(JID jid) {
		return this.addressing.find(jid);
	}

	public JIDContext findOne(JID jid) {
		return this.addressing.findOne(jid);
	}

	public ProxyProcessor broadcast(JID jid, JID from, JID to, Status status) {
		this.presenceQueue.broadcast(jid, from, to, status);
		return this;
	}

	public ProxyProcessor broadcast(JID jid, Protocol protocol) {
		this.protocolQueue.broadcast(jid, protocol);
		return this;
	}

	public ProxyProcessor establish(JID from, Relation relation) {
		this.relationContext.establish(from, relation);
		return this;
	}

	public ProxyProcessor update(JID from, JID to, String state) {
		this.relationContext.update(from, to, state);
		return this;
	}

	public ProxyProcessor remove(JID from, JID to) {
		this.relationContext.remove(from, to);
		return this;
	}

	public Relation ourRelation(JID from, JID to) {
		return this.relationContext.ourRelation(from, to);
	}

	public Set<Relation> myRelations(JID from) {
		return this.relationContext.myRelations(from);
	}
	
	public Set<String> whoSubscribedMe(JID from) {
		return this.relationContext.whoSubscribedMe(from);
	}

	public Set<String> iSubscribedWho(JID from) {
		return this.relationContext.iSubscribedWho(from);
	}
}