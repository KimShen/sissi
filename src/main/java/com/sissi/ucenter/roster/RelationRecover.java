package com.sissi.ucenter.roster;

import java.util.Set;

import com.sissi.context.JID;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2014年2月19日
 */
public interface RelationRecover {

	public Set<Relation> recover(JID jid);
}
