package com.sissi.ucenter.roster;

import com.sissi.context.JID;

/**
 * @author kim 2014年1月22日
 */
public interface RelationInductor {

	public RelationInductor update(JID master, JID slave);
	
	public RelationInductor remove(JID master, JID slave);
}
