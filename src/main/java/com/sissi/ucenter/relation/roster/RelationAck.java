package com.sissi.ucenter.relation.roster;

import java.util.Set;

import com.sissi.context.JID;
import com.sissi.ucenter.relation.Relation;

/**
 * 获取申请但尚未处理的订阅关系
 * 
 * @author kim 2014年2月19日
 */
public interface RelationAck {

	public Set<Relation> ack(JID jid);
}
