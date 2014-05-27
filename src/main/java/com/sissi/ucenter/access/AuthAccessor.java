package com.sissi.ucenter.access;

/**
 * 验证身份
 * 
 * @author kim 2013-10-24
 */
public interface AuthAccessor {

	/**
	 * @param username
	 * @param password 请求密码
	 * @return 原始密码
	 */
	public String access(String username, String password);
}
