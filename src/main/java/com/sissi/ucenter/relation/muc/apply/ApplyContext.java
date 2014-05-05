package com.sissi.ucenter.relation.muc.apply;

import com.sissi.context.JID;
import com.sissi.field.Fields;

/**
 * 请求处理器
 * 
 * @author kim 2014年3月12日
 */
public interface ApplyContext {

	/**
	 * @param invoker
	 * @param target
	 * @param fields
	 * @return 是否需要转发请求
	 */
	public boolean apply(JID invoker, JID target, Fields fields);
}
