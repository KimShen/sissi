package com.sisi.feed.chained;

import java.util.List;

import com.sisi.context.Context;
import com.sisi.feed.Feeder;
import com.sisi.feed.FeederBuilder;
import com.sisi.process.Processor;

/**
 * @author kim 2013-10-30
 */
public class ChainedFeederBuilder implements FeederBuilder {

	@Override
	public Feeder builder(Context context, List<Processor> processors) {
		return new ChainedFeeder(context, processors);
	}

}
