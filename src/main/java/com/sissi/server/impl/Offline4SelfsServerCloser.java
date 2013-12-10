package com.sissi.server.impl;

import com.sissi.context.JIDContext;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2013-11-20
 */
public class Offline4SelfsServerCloser implements ServerCloser {

	@Override
	public void close(JIDContext context) {
		context.getOnlineStatus().clear();
	}
}
