package com.sissi.ucenter.muc;

import java.util.Set;

import com.sissi.context.JID;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;

/**
 * @author kim 2014年3月17日
 */
public interface MucRelationContext extends RelationContext {

	public Set<Relation> ourRelations(JID from, JID to);

	public Set<Relation> myRelations(JID from, String status);
}
