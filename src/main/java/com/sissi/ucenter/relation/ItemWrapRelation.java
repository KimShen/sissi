package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation implements RelationRoster {

	private final RelationRoster relation;

	private final GroupItem item;

	public ItemWrapRelation(RelationRoster relation, GroupItem item) {
		super();
		this.relation = relation;
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
	public Boolean isAsk() {
		return this.relation.isActivate() ? this.relation.isAsk() : true;
	}

	@Override
	public String[] asGroups() {
		if (this.item.getGroup() != null) {
			Set<String> groups = new HashSet<String>();
			for (Group group : this.item.getGroup()) {
				groups.add(group.getValue());
			}
			return groups.toArray(new String[] {});
		} else {
			return null;
		}
	}

	@Override
	public String getSubscription() {
		return RosterSubscription.parse(this.relation.getSubscription()).toString();
	}

	public Boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.relation.getSubscription()).in(subscriptions);
	}

	public Boolean isActivate() {
		return this.relation.isActivate();
	}

	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("groups", this.asGroups());
		plus.put("ask", this.isAsk());
		return plus;
	}
}
