package com.sissi.looper.impl;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.resource.ResourceCounter;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;

/**
 * @author kim 2013-10-30
 */
public class PersonalLooperBuilder implements LooperBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final String resource = PersonalLooper.class.getSimpleName();

	private final ResourceCounter resourceCounter;

	private final Interval interval;

	private final Runner runner;

	private final int threadNumber;

	public PersonalLooperBuilder(Runner runner, Interval interval, int threadNumber, ResourceCounter resourceCounter) {
		super();
		this.runner = runner;
		this.interval = interval;
		this.threadNumber = threadNumber;
		this.resourceCounter = resourceCounter;
	}

	@Override
	public Looper build(Future<?> future, Feeder feeder) {
		return new PersonalLooper(future, feeder);
	}

	private class PersonalLooper implements Runnable, Looper {

		private final AtomicBoolean state = new AtomicBoolean();

		private final Future<?> future;

		private final Feeder feeder;

		private PersonalLooper(Future<?> future, Feeder feeder) {
			super();
			this.future = future;
			this.feeder = feeder;
		}

		@Override
		public void run() {
			try {
				PersonalLooperBuilder.this.resourceCounter.increment(PersonalLooperBuilder.this.resource);
				while (true) {
					try {
						if (this.prepareStop()) {
							break;
						}
						this.getAndFeed();
					} catch (Exception e) {
						PersonalLooperBuilder.this.log.warn(e.toString());
						Trace.trace(PersonalLooperBuilder.this.log, e);
					}
				}
			} finally {
				PersonalLooperBuilder.this.resourceCounter.decrement(PersonalLooperBuilder.this.resource);
			}
		}

		private void getAndFeed() throws Exception {
			Protocol protocol = Protocol.class.cast(future.get(PersonalLooperBuilder.this.interval.getInterval(), PersonalLooperBuilder.this.interval.getUnit()));
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
			PersonalLooperBuilder.this.runner.executor(PersonalLooperBuilder.this.threadNumber, this);
			return this;
		}

		@Override
		public Looper stop() {
			this.state.set(false);
			return this;
		}
	}
}
