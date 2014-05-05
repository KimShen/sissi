package com.sissi.ucenter.relation.muc.status.adder;

import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 岗位受限
 * 
 * @author kim 2014年3月16日
 */
public class Code321StatusAdder implements CodeStatusAdder {

	@Override
	public CodeStatus add(CodeStatus status) {
		return status.getItem().forbidden() ? status.add("321") : status;
	}

}
