package com.sissi.ucenter.relation.muc.status.adder;

import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 开启日志
 * 
 * @author kim 2014年3月5日
 */
public class Code170StatusAdder implements CodeStatusAdder {

	private final boolean log;

	public Code170StatusAdder(boolean log) {
		super();
		this.log = log;
	}

	@Override
	public CodeStatus add(CodeStatus status) {
		return status.loop() && this.log ? status.add("170") : status;
	}
}
