package com.sissi.ucenter.muc;

import com.sissi.ucenter.field.Field;

/**
 * @author kim 2014年3月25日
 */
public interface MucConfigParser {

	public Object parse(Field<?> field);
	
	public String field();

	public String support();
}
