package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sissi.context.JIDBuilder;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.RelationRoster;

/**
 * @author kim 2013-11-6
 */
public class ItemWrapRelation implements RelationRoster {

	private final GroupItem item;

	private final JIDBuilder jidBuilder;

	public ItemWrapRelation(JIDBuilder jidBuilder, GroupItem item) {
		super();
		this.item = item;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public String getJID() {
		return this.jidBuilder.build(this.item.getJid()).asStringWithBare();
	}

	@Override
	public String getName() {
		return this.item.getName();
	}

	@Override
	public String[] asGroups() {
		if (this.item.getGroup() != null) {
			Set<String> groups = new HashSet<String>();
			for (Group group : this.item.getGroup()) {
				groups.add(group.getValue());
			}
			return groups.toArray(new String[]{});
		} else {
			return null;
		}
	}

	@Override
	public String getSubscription() {
		return RosterSubscription.parse(this.item.getSubscription()).toString();
	}

	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put("groups", this.asGroups());
		return plus;
	}
}
