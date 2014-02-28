package com.sissi.ucenter.muc;

/**
 * @author kim 2014年2月21日
 */
public interface MucStatusComputer {

	public MucStatusComputer collect(MucStatusCollector collector, MucStatusJudge judge);
}
