package com.sissi.feed;

import com.sissi.context.Context;
import com.sissi.process.ProcessorFinder;

/**
 * @author kim 2013-10-30
 */
public interface FeederBuilder {

	public Feeder builder(Context context, ProcessorFinder finder);
}
