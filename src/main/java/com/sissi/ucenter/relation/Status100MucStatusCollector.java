package com.sissi.ucenter.relation;

import com.sissi.ucenter.MucStatus;
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusJudge;

/**
 * @author kim 2014年2月21日
 */
public class Status100MucStatusCollector implements MucStatusCollector {

	@Override
	public MucStatusCollector collect(MucStatus status, MucStatusJudge judge) {
		if (!judge.judge(MucStatusJudge.JUDEGE_HIDDEN, null)) {
			status.add("100");
		}
		return this;
	}
}
