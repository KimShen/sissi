package com.sissi.feed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipelineFinder;

/**
 * @author kim 2013-10-30
 */
public interface FeederBuilder {

	public Feeder build(JIDContext context, ProcessPipelineFinder finder);
}
