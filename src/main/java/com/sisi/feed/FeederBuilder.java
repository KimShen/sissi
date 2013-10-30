package com.sisi.feed;

import java.util.List;

import com.sisi.context.Context;
import com.sisi.process.Processor;

/**
 * @author kim 2013-10-30
 */
public interface FeederBuilder {

	public Feeder builder(Context context, List<Processor> processors);
}
