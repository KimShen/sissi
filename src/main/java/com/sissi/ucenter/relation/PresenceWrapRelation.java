package com.sissi.ucenter.relation;

import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年1月23日
 */
public class PresenceWrapRelation implements Relation {
	
	private final JIDBuilder jidBuilder;

	private Presence presence;

	private Relation current;

	public PresenceWrapRelation(JIDBuilder jidBuilder, Presence presence, Relation current) {
		super();
		this.jidBuilder = jidBuilder;
		this.presence = presence;
		this.current = current;
	}

	@Override
	public String getJID() {
		return this.jidBuilder.build(this.presence.getTo()).asStringWithBare();
	}

	@Override
	public String getName() {
		return this.current.getName();
	}

	@Override
	public String getSubscription() {
		return this.current.getSubscription();
	}

	@Override
	public Boolean isActivate() {
		return this.current.isActivate();
	}

	@Override
	public Map<String, Object> plus() {
		return this.current.plus();
	}
}
