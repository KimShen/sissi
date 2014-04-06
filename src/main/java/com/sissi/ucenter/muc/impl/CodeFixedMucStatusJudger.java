package com.sissi.ucenter.muc.impl;

import java.util.Set;

import com.sissi.ucenter.muc.MucStatus;
import com.sissi.ucenter.muc.MucStatusJudger;

/**
 * @author kim 2014年4月6日
 */
public class CodeFixedMucStatusJudger implements MucStatusJudger {

	private final Set<String> codes;

	public CodeFixedMucStatusJudger(Set<String> codes) {
		super();
		this.codes = codes;
	}

	@Override
	public MucStatus judege(MucStatus status) {
		return status.add(this.codes);
	}
}
