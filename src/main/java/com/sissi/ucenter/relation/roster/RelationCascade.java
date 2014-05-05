package com.sissi.ucenter.relation.roster;

import com.sissi.context.JID;

/**
 * 级联操作
 * 
 * @author kim 2014年1月22日
 */
public interface RelationCascade {

	public RelationCascade update(JID master, JID slave);

	public RelationCascade remove(JID master, JID slave);
}
