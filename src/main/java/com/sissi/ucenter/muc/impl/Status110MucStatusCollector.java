package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucStatusCollector;
import com.sissi.ucenter.muc.MucStatusComputer;
import com.sissi.ucenter.muc.MucStatusJudge;


/**
 * @author kim 2014年2月21日
 */
public class Status110MucStatusCollector implements MucStatusComputer {

	@Override
	public MucStatusComputer collect(MucStatusCollector status, MucStatusJudge judge) {
		if (status.judge(MucStatusJudge.JUDEGE_JID, judge.supply(MucStatusJudge.JUDEGE_JID))) {
			status.add("110");
		}
		return this;
	}
}
