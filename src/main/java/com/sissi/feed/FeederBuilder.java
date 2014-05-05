package com.sissi.feed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.InputFinder;

/**
 * @author kim 2013年12月23日
 */
public interface FeederBuilder {

	/**
	 * @param context
	 * @param finder XMPP协议栈
	 * @return
	 */
	public Feeder build(JIDContext context, InputFinder finder);
}