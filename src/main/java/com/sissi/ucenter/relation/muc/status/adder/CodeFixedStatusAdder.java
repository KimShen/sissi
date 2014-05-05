package com.sissi.ucenter.relation.muc.status.adder;

import java.util.Set;

import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * 固定状态码
 * 
 * @author kim 2014年4月6日
 */
public class CodeFixedStatusAdder implements CodeStatusAdder {

	private final Set<String> codes;

	public CodeFixedStatusAdder(Set<String> codes) {
		super();
		this.codes = codes;
	}

	@Override
	public CodeStatus add(CodeStatus status) {
		return status.add(this.codes);
	}
}
