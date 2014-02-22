package com.sissi.ucenter;

/**
 * @author kim 2014年2月21日
 */
public interface MucStatusComputer {

	public MucStatusComputer collect(MucStatusCollector collector, MucStatusJudge judge);
}
