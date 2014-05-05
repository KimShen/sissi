package com.sissi.server.ha;

import com.sissi.context.JIDContext;

/**
 * 心跳
 * 
 * @author kim 2014年1月8日
 */
public interface Keepalive {

	public Keepalive ping(JIDContext context);
}
