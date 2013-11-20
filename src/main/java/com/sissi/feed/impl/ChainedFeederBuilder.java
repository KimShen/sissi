package com.sissi.feed.impl;

import com.sissi.context.JIDContext;
import com.sissi.feed.Feeder;
import com.sissi.feed.FeederBuilder;
import com.sissi.pipeline.ProcessPipelineFinder;

/**
 * @author kim 2013-10-30
 */
public class ChainedFeederBuilder implements FeederBuilder {

	@Override
	public Feeder build(JIDContext context, ProcessPipelineFinder finder) {
		return new ChainedFeeder(context, finder);
	}
}
