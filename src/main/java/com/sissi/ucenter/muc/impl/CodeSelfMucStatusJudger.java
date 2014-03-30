package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月5日
 */
public class CodeSelfMucStatusJudger implements MucStatusJudger {

	@Override
	public MucStatus judege(MucStatus status) {
		return status.owner() && status.contain("201") ? !status.add("110").hidden() ? status.add("100") : status : status;
	}
}
