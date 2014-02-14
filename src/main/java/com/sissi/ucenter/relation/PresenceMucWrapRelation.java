package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation extends BitSetRelationMuc {

	private final JID jid;

	private final Relation relation;

	public PresenceMucWrapRelation(JID jid, Relation relation) {
		super(relation.getSubscription());
		this.jid = jid;
		this.relation = relation;
	}

	@Override
	public String getJID() {
		return this.jid.asStringWithBare();
	}

	public String getRoom() {
		return this.jid.user();
	}

	@Override
	public String getName() {
		return this.jid.resource();
	}

	@Override
	public boolean isActivate() {
		return this.relation.isActivate();
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("room", this.getRoom());
		return plus;
	}

	@Override
	public boolean in(String... subscriptions) {
		return false;
	}
}
