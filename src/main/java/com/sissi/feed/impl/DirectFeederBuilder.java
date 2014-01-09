package com.sissi.feed.impl;

import com.sissi.context.JIDContext;
import com.sissi.feed.Feeder;
import com.sissi.feed.FeederBuilder;
import com.sissi.pipeline.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-30
 */
public class DirectFeederBuilder implements FeederBuilder {

	@Override
	public Feeder build(JIDContext context, InputFinder finder) {
		return new DirectFeeder(finder, context);
	}

	private class DirectFeeder implements Feeder {

		private final InputFinder finder;

		private final JIDContext context;

		public DirectFeeder(InputFinder finder, JIDContext context) {
			super();
			this.finder = finder;
			this.context = context;
		}

		@Override
		public Feeder feed(Protocol protocol) {
			this.finder.find(protocol).input(this.context, protocol);
			return this;
		}
	}
}
