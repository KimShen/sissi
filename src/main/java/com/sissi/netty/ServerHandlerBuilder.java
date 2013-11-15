package com.sissi.netty;

import java.io.IOException;

import com.sissi.feed.FeederBuilder;
import com.sissi.looper.LooperBuilder;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.read.Reader;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-30
 */
public class ServerHandlerBuilder {

	private final Writer writer;

	private final Reader reader;

	private final ProcessPipelineFinder finder;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder connectorBuilder;

	public ServerHandlerBuilder(Writer writer, Reader reader, ProcessPipelineFinder finder, FeederBuilder feederBuilder, LooperBuilder connectorBuilder) {
		super();
		this.writer = writer;
		this.reader = reader;
		this.finder = finder;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public ServerHandler builder() throws IOException {
		return new ServerHandler(this.reader, this.writer, this.finder, this.feederBuilder, this.connectorBuilder);
	}
}
