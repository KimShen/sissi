package com.sissi.ucenter.roster;

import com.sissi.ucenter.Relation;

/**
 * @author kim 2013年12月30日
 */
public interface RelationRoster extends Relation {

	public String getSubscription();

	public String[] asGroups();

	public boolean isAsk();

	public boolean in(String... subscriptions);
}