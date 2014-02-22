package com.sissi.ucenter.relation;

import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusComputer;
import com.sissi.ucenter.MucStatusJudge;

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
