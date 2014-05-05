package com.sissi.ucenter.relation;

import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2013-11-13
 */
public interface RelationContext {

	/**
	 * 建立订阅关系
	 * 
	 * @param from 发起人
	 * @param relation 初始关系
	 * @return
	 */
	public RelationContext establish(JID from, Relation relation);

	/**
	 * 更新订阅关系
	 * 
	 * @param from 发起人
	 * @param to 接收人
	 * @param state 订阅关系状态
	 * @return
	 */
	public RelationContext update(JID from, JID to, String state);

	/**
	 * 解除订阅关系
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public RelationContext remove(JID from, JID to);

	/**
	 * 获取订阅关系
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Relation ourRelation(JID from, JID to);

	/**
	 * 所有订阅关系
	 * 
	 * @param from
	 * @return
	 */
	public Set<Relation> myRelations(JID from);

	/**
	 * 谁订阅了我
	 * 
	 * @param from
	 * @return
	 */
	public Set<JID> whoSubscribedMe(JID from);

	/**
	 * 我订阅了谁
	 * 
	 * @param from
	 * @return
	 */
	public Set<JID> iSubscribedWho(JID from);
}