package com.sissi.ucenter.muc.impl;

import java.util.List;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年3月5日
 */
public class ChainedMucStatusJudger implements MucStatusJudger {

	private final List<MucStatusJudger> judegers;

	public ChainedMucStatusJudger(List<MucStatusJudger> judegers) {
		super();
		this.judegers = judegers;
	}

	@Override
	public MucStatus judege(MucStatus status) {
		for (MucStatusJudger each : this.judegers) {
			each.judege(status);
		}
		return status;
	}
}
