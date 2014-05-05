package com.sissi.ucenter.relation.muc.validate.status;

import com.sissi.protocol.Error;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;

/**
 * 无错的ItemStatus
 * 
 * @author kim 2014年4月19日
 */
public class ValidItemStatus implements ItemStatus {

	public final static ItemStatus INSTANCE = new ValidItemStatus();

	private ValidItemStatus() {

	}

	@Override
	public boolean valid() {
		return true;
	}

	@Override
	public Error error() {
		return null;
	}
}
