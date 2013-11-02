package com.sissi.netty;

import java.io.IOException;
import java.util.List;

import com.sissi.connector.ConnectorBuilder;
import com.sissi.feed.FeederBuilder;
import com.sissi.group.Group;
import com.sissi.process.Processor;
import com.sissi.read.Reader;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-30
 */
public class ServerHandlerBuilder {

	private final Writer writer;

	private final Reader reader;

	private final List<Processor> processors;

	private final FeederBuilder feederBuilder;

	private final ConnectorBuilder connectorBuilder;

	public ServerHandlerBuilder(Writer writer, Reader reader, List<Processor> processors, FeederBuilder feederBuilder, ConnectorBuilder connectorBuilder) {
		super();
		this.writer = writer;
		this.reader = reader;
		this.processors = processors;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public ServerHandler builder(Group group) throws IOException {
		return new ServerHandler(group, this.reader, this.writer, this.processors, this.feederBuilder, this.connectorBuilder);
	}
}
