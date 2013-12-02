package com.sissi.feed.impl;

import com.sissi.context.JIDContext;
import com.sissi.feed.Feeder;
import com.sissi.feed.Feeder.FeederBuilder;
import com.sissi.pipeline.Input.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-30
 */
public class DirectFeederBuilder implements FeederBuilder {

	@Override
	public Feeder build(JIDContext context, InputFinder finder) {
		return new DirectFeeder(context, finder);
	}

	private class DirectFeeder implements Feeder {

		private final JIDContext context;

		private final InputFinder finder;

		public DirectFeeder(JIDContext context, InputFinder finder) {
			super();
			this.context = context;
			this.finder = finder;
		}

		@Override
		public void feed(Protocol protocol) {
			this.finder.find(protocol).input(this.context, protocol);
		}
	}
}
