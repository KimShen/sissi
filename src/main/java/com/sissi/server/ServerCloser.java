package com.sissi.server;

import com.sissi.context.JIDContext;

/**
 * @author kim 2013-11-20
 */
public interface ServerCloser {

	public void close(JIDContext context);
}
