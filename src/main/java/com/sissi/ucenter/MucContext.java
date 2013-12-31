package com.sissi.ucenter;

import java.util.Set;

import com.sissi.context.JID;

/**
 * @author kim 2013年12月30日
 */
public interface MucContext {

	public Set<Relation> myMembers(JID to);
}
