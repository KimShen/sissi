package com.sissi.ucenter.relation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2014年2月13日
 */
public class NoneRelation implements Relation, RelationRoster, RelationMuc {

	private static final Map<String, Object> fieldPlus = Collections.unmodifiableMap(new HashMap<String, Object>());

	private static final String zero = "0";

	private final JID jid;

	private final String subscription;

	public NoneRelation(JID jid, String subscription) {
		super();
		this.jid = jid;
		this.subscription = subscription;
	}

	public NoneRelation(JID jid) {
		this(jid, zero);
	}

	@Override
	public String getJID() {
		return this.jid.asStringWithBare();
	}

	public String getRoom() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getSubscription() {
		return this.subscription;
	}

	public String getRole() {
		return ItemRole.VISITOR.toString();
	}

	public String getAffiliaion() {
		return ItemAffiliation.MEMBER.toString();
	}

	public boolean in(String... subscriptions) {
		return false;
	}

	public boolean in(RosterSubscription... subscriptions) {
		return false;
	}

	public boolean isActivate() {
		return false;
	}

	@Override
	public boolean isAsk() {
		return false;
	}

	@Override
	public Map<String, Object> plus() {
		return fieldPlus;
	}

	@Override
	public String[] asGroups() {
		return null;
	}

}