package com.sissi.ucenter.relation.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDs;

/**
 * @author kim 2014年2月17日
 */
public interface MucRelationMapping {

	/**
	 * MUC JID转换为JID(多资源)
	 * 
	 * @param group
	 * @return
	 */
	public JIDs mapping(JID group);
}
