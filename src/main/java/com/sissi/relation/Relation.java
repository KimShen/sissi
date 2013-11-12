package com.sissi.relation;

import java.util.Map;

/**
 * @author kim 2013-11-6
 */
public interface Relation {

	public String getJid();

	public String getName();

	public String getGroup();
	
	public String getSubscription();
	
	public Map<String, Object> toEntity();
}
