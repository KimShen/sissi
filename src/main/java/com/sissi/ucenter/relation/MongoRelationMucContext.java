package com.sissi.ucenter.relation;

import java.util.Set;

import com.sissi.context.JID;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationContext;


/**
 * @author kim 2014年2月11日
 */
public class MongoRelationMucContext implements RelationContext {

	@Override
	public RelationContext establish(JID from, Relation relation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationContext update(JID from, JID to, String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationContext remove(JID from, JID to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation ourRelation(JID from, JID to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Relation> myRelations(JID from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<JID> whoSubscribedMe(JID from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<JID> iSubscribedWho(JID from) {
		// TODO Auto-generated method stub
		return null;
	}

}
