package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月16日
 */
public class Code321MucStatusJudger implements MucStatusJudger {

	@Override
	public MucStatus judege(MucStatus status) {
		return status.getItem().refuse() && status.contain("201") ? status.add("321") : status;
	}

}
