package com.sissi.server;

import com.sissi.context.JIDContext;

/**
 * @author kim 2014年1月8日
 */
public interface ServerHeart {

	public Long ping(JIDContext context);
}
