package com.sissi.ucenter;

import com.sissi.protocol.iq.roster.RosterSubscription;

/**
 * @author kim 2013年12月30日
 */
public interface RelationRoster extends Relation {

	public String getSubscription();

	public String[] asGroups();

	public boolean isAsk();

	public boolean in(String... subscriptions);

	public boolean in(RosterSubscription... subscriptions);
}