package com.sissi.relation.roster;

import java.util.HashMap;
import java.util.Map;

import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.protocol.iq.roster.Roster.Subscription;
import com.sissi.relation.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation implements RelationRoster {

	private Item item;

	public ItemWrapRelation(Item item) {
		super();
		this.item = item;
	}

	@Override
	public String getJID() {
		return this.item.getJid();
	}

	@Override
	public String getName() {
		return this.item.getName();
	}

	@Override
	public String getGp() {
		Group gp = this.item.getGroup();
		return gp != null ? gp.getText() : null;
	}

	@Override
	public String getSubscription() {
		return Subscription.parse(this.item.getSubscription()).toString();
	}

	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("group", this.getGp());
		return plus;
	}
}
