package com.sissi.feed.impl;

import com.sissi.context.JIDContext;
import com.sissi.feed.Feeder;
import com.sissi.feed.FeederBuilder;
import com.sissi.pipeline.InputCondition.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-30
 */
public class MyFeederBuilder implements FeederBuilder {

	@Override
	public Feeder build(JIDContext context, InputFinder finder) {
		return new MyFeeder(context, finder);
	}

	private class MyFeeder implements Feeder {

		private JIDContext context;

		private InputFinder finder;

		public MyFeeder(JIDContext context, InputFinder finder) {
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
