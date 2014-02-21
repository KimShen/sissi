package com.sissi.ucenter.relation;

import java.util.List;

import com.sissi.ucenter.MucStatus;
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusJudge;

/**
 * @author kim 2014年2月21日
 */
public class ChainedMucStatusCollector implements MucStatusCollector {

	private final List<MucStatusCollector> chained;

	public ChainedMucStatusCollector(List<MucStatusCollector> chained) {
		super();
		this.chained = chained;
	}

	@Override
	public MucStatusCollector collect(MucStatus status, MucStatusJudge judge) {
		for (MucStatusCollector collector : this.chained) {
			collector.collect(status, judge);
		}
		return this;
	}
}
