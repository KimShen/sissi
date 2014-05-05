package com.sissi.ucenter.relation.roster;

import com.sissi.ucenter.relation.Relation;

/**
 * Roster订阅关系
 * 
 * @author kim 2013年12月30日
 */
public interface RosterRelation extends Relation {

	/**
	 * 是否为ACK
	 * 
	 * @return
	 */
	public boolean ask();

	/**
	 * 分组
	 * 
	 * @return
	 */
	public String[] groups();

	/**
	 * 订阅关系
	 * 
	 * @return
	 */
	public String subscription();

	/**
	 * 是否包含指定订阅关系
	 * 
	 * @param subscriptions
	 * @return
	 */
	public boolean in(String... subscriptions);
}