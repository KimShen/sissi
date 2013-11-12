package com.sissi.feed.chained;

import com.sissi.context.Context;
import com.sissi.feed.Feeder;
import com.sissi.feed.FeederBuilder;
import com.sissi.process.ProcessorFinder;

/**
 * @author kim 2013-10-30
 */
public class ChainedFeederBuilder implements FeederBuilder {

	@Override
	public Feeder builder(Context context, ProcessorFinder finder) {
		return new ChainedFeeder(context, finder);
	}
}
