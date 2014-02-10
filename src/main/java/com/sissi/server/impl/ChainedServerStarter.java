package com.sissi.server.impl;

import java.util.List;

import com.sissi.server.ServerStarter;

/**
 * @author kim 2013年12月23日
 */
public class ChainedServerStarter implements ServerStarter {

	private final List<ServerStarter> starters;

	public ChainedServerStarter(List<ServerStarter> starters) {
		super();
		this.starters = starters;
	}

	@Override
	public ServerStarter start() {
		for (ServerStarter each : this.starters) {
			each.start();
		}
		return this;
	}

	@Override
	public ServerStarter stop() {
		for (ServerStarter each : this.starters) {
			each.stop();
		}
		return this;
	}
}
