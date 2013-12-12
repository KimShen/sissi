package com.sissi.server.impl;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2013年11月30日
 */
public class ChainedServerCloser implements ServerCloser {

	private final List<ServerCloser> closers;

	public ChainedServerCloser(List<ServerCloser> closers) {
		super();
		this.closers = closers;
	}

	@Override
	public ChainedServerCloser close(JIDContext context) {
		for (ServerCloser each : this.closers) {
			each.close(context);
		}
		return this;
	}
}
