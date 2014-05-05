package com.sissi.ucenter.relation.muc.status.adder;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 岗位为Outcast
 * 
 * @author kim 2014年3月16日
 */
public class Code301StatusAdder implements CodeStatusAdder {

	@Override
	public CodeStatus add(CodeStatus status) {
		return ItemAffiliation.OUTCAST.equals(status.getItem().getAffiliation()) ? status.add("301") : status;
	}
}
