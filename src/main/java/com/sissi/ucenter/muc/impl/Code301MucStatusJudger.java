package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月16日
 */
public class Code301MucStatusJudger implements MucStatusJudger {

	@Override
	public MucStatus judege(MucStatus status) {
		return ItemAffiliation.OUTCAST.equals(status.getItem().getAffiliation()) ? status.add("301") : status;
	}

}
