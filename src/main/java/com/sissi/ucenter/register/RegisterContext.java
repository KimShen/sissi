package com.sissi.ucenter.register;

import com.sissi.field.Fields;

/**
 * 注册
 * 
 * @author kim 2013年12月3日
 */
public interface RegisterContext {

	/**
	 * @param username
	 * @param fields
	 * @return 是否注册成功
	 */
	public boolean register(String username, Fields fields);

	/**
	 * @param username
	 * @return 是否注销成功
	 */
	public boolean unregister(String username);
}
