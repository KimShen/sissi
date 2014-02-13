package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.muc.PresenceMucSubscription;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation implements RelationMuc {

	private final JID group;

	public PresenceMucWrapRelation(JIDBuilder jidBuilder, Presence presence) {
		super();
		this.group = jidBuilder.build(presence.getTo());
	}

	@Override
	public String getJID() {
		return this.group.asStringWithBare();
	}

	@Override
	public String getName() {
		return this.group.resource();
	}

	@Override
	public String getSubscription() {
		return PresenceMucSubscription.BOTH.toString();
	}

	@Override
	public boolean isActivate() {
		return true;
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		return plus;
	}

	@Override
	public boolean in(String... subscriptions) {
		return false;
	}
}
