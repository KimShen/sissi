package com.sissi.feed;

import java.util.List;

import com.sissi.context.Context;
import com.sissi.process.Processor;

/**
 * @author kim 2013-10-30
 */
public interface FeederBuilder {

	public Feeder builder(Context context, List<Processor> processors);
}
