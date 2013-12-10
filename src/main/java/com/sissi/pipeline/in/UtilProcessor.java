package com.sissi.pipeline.in;

import com.sissi.addressing.Addressing;
import com.sissi.broadcast.PresenceBroadcast;
import com.sissi.broadcast.ProtocolBraodcast;
import com.sissi.context.JID;
import com.sissi.context.JID.JIDBuilder;
import com.sissi.pipeline.Input;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2013-11-16
 */
abstract public class UtilProcessor implements Input {

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

	protected JID build(String jid) {
		return this.jidBuilder.build(jid);
	}

	protected JID build(String username, String resource) {
		return this.jidBuilder.build(username, resource);
	}
}