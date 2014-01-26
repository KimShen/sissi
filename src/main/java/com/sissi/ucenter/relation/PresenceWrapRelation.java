package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年1月23日
 */
public class PresenceWrapRelation implements Relation {

	private final JIDBuilder jidBuilder;

	private final Presence presence;

	private final Relation current;

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
	
	public Boolean in(String... subscriptions){
		return RosterSubscription.parse(this.current.getSubscription()).in(subscriptions);
	}

	@Override
	public Boolean isActivate() {
		return this.current.isActivate();
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("ask", true);
		return plus;
	}
}
