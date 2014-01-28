package com.sissi.ucenter.relation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年1月28日
 */
public class NoneRelation implements Relation, RelationRoster {

	protected final static Map<String, Object> plus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private final JID jid;

	public NoneRelation(JID jid) {
		super();
		this.jid = jid;
	}

	@Override
	public String getJID() {
		return this.jid.asStringWithBare();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getSubscription() {
		return RosterSubscription.NONE.toString();
	}

	public Boolean in(String... subscriptions) {
		return false;
	}

	public Boolean isActivate() {
		return false;
	}

	@Override
	public Boolean isAsk() {
		return false;
	}

	@Override
	public Map<String, Object> plus() {
		return plus;
	}

	@Override
	public String[] asGroups() {
		return null;
	}
}
