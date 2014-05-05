package com.sissi.ucenter.relation.muc.status.adder;

import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 角色滞空(None)
 * 
 * @author kim 2014年3月16日
 */
public class Code307StatusAdder implements CodeStatusAdder {

	@Override
	public CodeStatus add(CodeStatus status) {
		return ItemRole.NONE.equals(status.getItem().getRole()) ? status.add("307") : status;
	}

}
