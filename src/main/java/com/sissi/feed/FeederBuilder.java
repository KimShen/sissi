package com.sissi.feed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.InputCondition.InputFinder;

/**
 * @author kim 2013-10-30
 */
public interface FeederBuilder {

	public Feeder build(JIDContext context, InputFinder finder);
}
