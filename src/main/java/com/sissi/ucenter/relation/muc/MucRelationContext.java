package com.sissi.ucenter.relation.muc;

import java.util.Set;

import com.sissi.context.JID;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.RelationContext;

/**
 * @author kim 2014年3月17日
 */
public interface MucRelationContext extends RelationContext {

	/**
	 * 获取订阅关系,单JID具有多个不同昵称的MUC JID
	 * 
	 * @param jid
	 * @param group
	 * @return
	 */
	public Set<Relation> ourRelations(JID jid, JID group);

	/**
	 * 根据角色/岗位获取订阅关系
	 * 
	 * @param group
	 * @param status
	 * @return
	 */
	public Set<Relation> myRelations(JID group, String status);
}
