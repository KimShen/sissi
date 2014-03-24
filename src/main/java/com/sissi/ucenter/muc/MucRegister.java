package com.sissi.ucenter.muc;

import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年3月24日
 */
public interface MucRegister {

	public <T extends Fields> T register(Fields source, T target);
}
