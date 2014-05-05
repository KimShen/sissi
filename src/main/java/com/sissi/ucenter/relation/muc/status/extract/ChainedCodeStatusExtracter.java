package com.sissi.ucenter.relation.muc.status.extract;

import java.util.List;

import com.sissi.field.Fields;
import com.sissi.ucenter.relation.muc.status.CodeStatus;
import com.sissi.ucenter.relation.muc.status.CodeStatusExtracter;

/**
 * @author kim 2014年3月27日
 */
public class ChainedCodeStatusExtracter implements CodeStatusExtracter {

	private final List<CodeStatusExtracter> extracters;

	public ChainedCodeStatusExtracter(List<CodeStatusExtracter> extracters) {
		super();
		this.extracters = extracters;
	}

	@Override
	public CodeStatus extract(Fields fields, CodeStatus status) {
		for (CodeStatusExtracter extracter : extracters) {
			extracter.extract(fields, status);
		}
		return status;
	}
}
