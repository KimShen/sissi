package com.sissi.ucenter.relation.muc.register;

import com.sissi.field.Fields;

/**
 * 房间注册
 * 
 * @author kim 2014年3月24日
 */
public interface RegisterContext {

	public <T extends Fields> T register(Fields source, T target);
}
