package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月16日
 */
public class Code307MucStatusJudger implements MucStatusJudger {

	@Override
	public MucStatus judege(MucStatus status) {
		return ItemRole.NONE.equals(status.getItem().getRole()) ? status.add("307") : status;
	}

}
