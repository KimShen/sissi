package com.sissi.ucenter.relation.roster.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sissi.config.Dictionary;
import com.sissi.protocol.iq.roster.Group;
import com.sissi.protocol.iq.roster.GroupItem;
import com.sissi.protocol.iq.roster.RosterSubscription;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.roster.RosterRelation;

/**
 * Roster Item包装</p><query xmlns='jabber:iq:roster'><item jid='romeo@example.net' name='Romeo' subscription='both'><group>Friends</group></item>...</query>
 * 
 * @author kim 2013-11-6
 */
public class ItemRosterRelation implements RosterRelation {

	private final RosterRelation relation;

	private final GroupItem item;

	public ItemRosterRelation(RosterRelation relation, GroupItem item) {
		super();
		this.relation = relation;
		this.item = item;
	}

	@Override
	public String jid() {
		return this.item.getJid();
	}

	@Override
	public String name() {
		return this.item.getName();
	}

	@Override
	public boolean ask() {
		return this.relation.activate() ? this.relation.ask() : true;
	}

	@Override
	public String[] groups() {
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

	public boolean activate() {
		return this.relation.activate();
	}

	@Override
	public String subscription() {
		return this.relation.subscription();
	}

	public boolean in(String... subscriptions) {
		return RosterSubscription.parse(this.relation.subscription()).in(subscriptions);
	}

	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		if (this.ask()) {
			plus.put(Dictionary.FIELD_ACK, this.ask());
		}
		plus.put(Dictionary.FIELD_GROUPS, this.groups());
		return plus;
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
