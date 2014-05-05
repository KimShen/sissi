package com.sissi.ucenter.relation.muc.validate;

import com.sissi.protocol.Error;

/**
 * Item有效性状态
 * 
 * @author kim 2014年4月19日
 */
public interface ItemStatus {

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	public boolean valid();

	public Error error();
}
