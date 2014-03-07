package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月5日
 */
public class Code303MucStatusJudger implements MucStatusJudger {

	@Override
	public MucStatus judege(MucStatus status) {
		return status.add("303");
	}
}
