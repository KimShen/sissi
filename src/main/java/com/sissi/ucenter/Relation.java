package com.sissi.ucenter;

import java.util.Map;

/**
 * @author kim 2013年12月30日
 */
public interface Relation {

	public String getJID();

	public String getName();

	public String getSubscription();

	public Boolean in(String... subscriptions);

	public Boolean isActivate();

	public Map<String, Object> plus();
}