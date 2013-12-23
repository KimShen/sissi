package com.sissi.feed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.InputFinder;

/**
 * @author kim 2013年12月23日
 */
public interface FeederBuilder {

	public Feeder build(JIDContext context, InputFinder finder);
}