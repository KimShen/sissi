package com.sissi.ucenter.relation.muc.status.adder;

import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 回路
 * 
 * @author kim 2014年3月5日
 */
public class CodeLoopStatusAdder implements CodeStatusAdder {

	@Override
	public CodeStatus add(CodeStatus status) {
		return status.loop() ? !status.add("110").hidden() ? status.add("100") : status : status;
	}
}
