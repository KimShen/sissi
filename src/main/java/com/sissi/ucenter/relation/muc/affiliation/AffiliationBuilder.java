package com.sissi.ucenter.relation.muc.affiliation;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月9日
 */
public interface AffiliationBuilder {

	public AffiliationUpdater build(JID group);
}
