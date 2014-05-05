package com.sissi.ucenter.relation.muc.status.adder;

import java.util.List;

import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusAdder;

/**
 * @author kim 2014年3月5日
 */
public class ChainedCodeStatusAdder implements CodeStatusAdder {

	private final List<CodeStatusAdder> judegers;

	public ChainedCodeStatusAdder(List<CodeStatusAdder> judegers) {
		super();
		this.judegers = judegers;
	}

	@Override
	public CodeStatus add(CodeStatus status) {
		for (CodeStatusAdder each : this.judegers) {
			each.add(status);
		}
		return status;
	}
}
