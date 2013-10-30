package com.sisi.netty;

import java.io.IOException;
import java.util.List;

import com.sisi.context.Group;
import com.sisi.feed.ConnectorBuilder;
import com.sisi.feed.FeederBuilder;
import com.sisi.process.Processor;

/**
 * @author kim 2013-10-30
 */
public class ServerHandlerBuilder {

	private final List<Processor> processors;

	private final FeederBuilder feederBuilder;

	private final ConnectorBuilder connectorBuilder;

	public ServerHandlerBuilder(List<Processor> processors, FeederBuilder feederBuilder, ConnectorBuilder connectorBuilder) {
		super();
		this.processors = processors;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public ServerHandler builder(Group group) throws IOException {
		return new ServerHandler(group, this.processors, this.feederBuilder, this.connectorBuilder);
	}
}
