package com.sissi.looper.impl;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2013-10-30
 */
public class PrivateLooperBuilder implements LooperBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final Runner runner;

	private final Integer threads;

	private final Interval interval;

	private final ResourceCounter resourceCounter;

	public PrivateLooperBuilder(Runner runner, Interval interval, Integer threads, ResourceCounter resourceCounter) {
		super();
		this.runner = runner;
		this.threads = threads;
		this.interval = interval;
		this.resourceCounter = resourceCounter;
	}

	@Override
	public Looper build(Future<?> future, Feeder feeder) {
		return new PrivateLooper(future, feeder);
	}

	private class PrivateLooper implements Runnable, Looper {

		private final AtomicBoolean state;

		private final Future<?> future;

		private final Feeder feeder;

		private PrivateLooper(Future<?> future, Feeder feeder) {
			super();
			this.state = new AtomicBoolean();
			this.future = future;
			this.feeder = feeder;
		}

		@Override
		public void run() {
			try {
				PrivateLooperBuilder.this.resourceCounter.increment();
				while (true) {
					try {
						if (this.prepareStop()) {
							break;
						}
						this.getAndFeed();
					} catch (Exception e) {
						if (PrivateLooperBuilder.this.log.isDebugEnabled()) {
							PrivateLooperBuilder.this.log.debug(e.toString());
							e.printStackTrace();
						}
					}
				}
			} finally {
				PrivateLooperBuilder.this.resourceCounter.decrement();
			}
		}

		private void getAndFeed() throws Exception {
			Protocol protocol = (Protocol) future.get(PrivateLooperBuilder.this.interval.getInterval(), PrivateLooperBuilder.this.interval.getUnit());
			if (protocol != null) {
				this.feeder.feed(protocol);
			}
		}

		private boolean prepareStop() {
			return this.state.get() == false;
		}

		@Override
		public Looper start() {
			this.state.set(true);
			PrivateLooperBuilder.this.runner.executor(PrivateLooperBuilder.this.threads, this);
			return this;
		}

		@Override
		public Looper stop() {
			this.state.set(false);
			return this;
		}
	}
}
