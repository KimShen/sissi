package com.sissi.ucenter.relation.muc.validate.status;

import com.sissi.protocol.Error;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;

/**
 * 错误的ItemStatus
 * 
 * @author kim 2014年4月19日
 */
public class WrongItemStatus implements ItemStatus {

	private final Error error;

	public WrongItemStatus(Error error) {
		super();
		this.error = error;
	}

	@Override
	public boolean valid() {
		return false;
	}

	@Override
	public Error error() {
		return this.error;
	}
}
