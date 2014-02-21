package com.sissi.ucenter.relation;

import com.sissi.ucenter.MucStatus;
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusJudge;

/**
 * @author kim 2014年2月21日
 */
public class Status110MucStatusCollector implements MucStatusCollector {

	@Override
	public MucStatusCollector collect(MucStatus status, MucStatusJudge judge) {
		if (status.judge(MucStatusJudge.JUDEGE_JID, judge.supply(MucStatusJudge.JUDEGE_JID))) {
			status.add("110");
		}
		return this;
	}
}
