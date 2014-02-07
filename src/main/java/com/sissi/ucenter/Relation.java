package com.sissi.ucenter;

import java.util.Map;

/**
 * @author kim 2013年12月30日
 */
public interface Relation {

	public String getJID();

	public String getName();

	public String getSubscription();

	public boolean isActivate();

	public boolean in(String... subscriptions);
	
	public Map<String, Object> plus();
}