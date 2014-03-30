package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月5日
 */
public class Code170MucStatusJudger implements MucStatusJudger {

	private final boolean log;

	public Code170MucStatusJudger(boolean log) {
		super();
		this.log = log;
	}

	@Override
	public MucStatus judege(MucStatus status) {
		return status.owner() && status.contain("201") && this.log ? status.add("170") : status;
	}
}
