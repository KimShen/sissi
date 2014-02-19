package com.sissi.ucenter;

import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2014年2月19日
 */
public interface RelationRecover {

	public Set<Relation> recover(JID jid);
}
