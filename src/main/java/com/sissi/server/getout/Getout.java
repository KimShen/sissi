package com.sissi.server.getout;

import com.sissi.context.JIDContext;

/**
 * 登出监听, 由于异常断开或其他非正常登出的情况, 应由网络层(如Netty)回调.
 * 
 * @author kim 2013-11-20
 */
public interface Getout {

	public Getout getout(JIDContext context);
}
