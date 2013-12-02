package com.sissi.feed;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-23
 */
public interface Feeder {

	public void feed(Protocol protocol);

	public interface FeederBuilder {

		public Feeder build(JIDContext context, InputFinder finder);
	}
}
