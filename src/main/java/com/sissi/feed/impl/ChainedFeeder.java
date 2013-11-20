package com.sissi.feed.impl;

import com.sissi.context.JIDContext;
import com.sissi.feed.Feeder;
import com.sissi.pipeline.ProcessPipelineFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ChainedFeeder implements Feeder {

	private JIDContext context;

	private ProcessPipelineFinder finder;

	public ChainedFeeder(JIDContext context, ProcessPipelineFinder finder) {
		super();
		this.context = context;
		this.finder = finder;
	}

	@Override
	public void feed(Protocol protocol) {
		this.finder.find(protocol).process(this.context, protocol);
	}
}
