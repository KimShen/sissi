package com.sissi.feed.chained;

import com.sissi.context.Context;
import com.sissi.feed.Feeder;
import com.sissi.process.ProcessorFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ChainedFeeder implements Feeder {

	private Context context;

	private ProcessorFinder finder;

	public ChainedFeeder(Context context, ProcessorFinder finder) {
		super();
		this.context = context;
		this.finder = finder;
	}

	@Override
	public void feed(Protocol protocol) {
		this.finder.find(protocol).process(this.context, protocol);
	}
}
