package com.sissi.ucenter.relation.muc.apply.register;

import com.sissi.context.JID;
import com.sissi.field.Fields;

/**
 * 同意更新个人信息请求(Information).
 * 
 * @author kim 2014年5月4日
 */
public class MongoRegisterApproveContext extends MongoRegisterApplyContext {

	public MongoRegisterApproveContext() {
		super("1");
	}

	@Override
	public boolean apply(JID invoker, JID group, Fields fields) {
		return false;
	}
}
