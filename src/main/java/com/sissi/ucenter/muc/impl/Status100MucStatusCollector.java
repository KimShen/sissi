package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatusCollector;
import com.sissi.ucenter.muc.MucStatusComputer;
import com.sissi.ucenter.muc.MucStatusJudge;


/**
 * @author kim 2014年2月21日
 */
public class Status100MucStatusCollector implements MucStatusComputer {

	@Override
	public MucStatusComputer collect(MucStatusCollector status, MucStatusJudge judge) {
		if (!judge.judge(MucStatusJudge.JUDEGE_HIDDEN, null)) {
			status.add("100");
		}
		return this;
	}
}
