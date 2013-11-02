package com.sissi.feed.chained;

import java.util.List;

import com.sissi.context.Context;
import com.sissi.feed.Feeder;
import com.sissi.feed.FeederBuilder;
import com.sissi.process.Processor;

/**
 * @author kim 2013-10-30
 */
public class ChainedFeederBuilder implements FeederBuilder {

	@Override
	public Feeder builder(Context context, List<Processor> processors) {
		return new ChainedFeeder(context, processors);
	}

}
