package com.sissi.ucenter.relation.muc.affiliation;

import com.sissi.context.JID;

/**
 * 岗位更新器
 * 
 * @author kim 2014年3月9日
 */
public interface AffiliationUpdater {

	public boolean update(JID jid, String affiliation);
}
