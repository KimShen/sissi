package com.sissi.ucenter.relation.muc.validate;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * Item有效性校验(MUC#admin)
 * 
 * @author kim 2014年4月19日
 */
public interface ItemValidator {

	/**
	 * @param invoker 发起人
	 * @param group 
	 * @param item Item.jid
	 * @return
	 */
	public ItemStatus valdate(JIDContext invoker, JID group, JID item);
}
