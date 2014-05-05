package com.sissi.server.impl;

import java.util.List;

import com.sissi.server.Server;

/**
 * @author kim 2013年12月23日
 */
public class ChainedServer implements Server {

	private final List<Server> starters;

	public ChainedServer(List<Server> starters) {
		super();
		this.starters = starters;
	}

	@Override
	public Server start() {
		for (Server each : this.starters) {
			each.start();
		}
		return this;
	}

	@Override
	public Server stop() {
		for (Server each : this.starters) {
			each.stop();
		}
		return this;
	}
}
