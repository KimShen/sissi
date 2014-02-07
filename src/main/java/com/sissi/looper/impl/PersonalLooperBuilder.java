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
public class PersonalLooperBuilder implements LooperBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final String resource = PersonalLooper.class.getSimpleName();

	private final Runner runner;

	private final Interval interval;
	
	private final int threadNumber;

	private final ResourceCounter resourceCounter;

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

		private final AtomicBoolean state;

		private final Future<?> future;

		private final Feeder feeder;

		private PersonalLooper(Future<?> future, Feeder feeder) {
			super();
			this.state = new AtomicBoolean();
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
						if (PersonalLooperBuilder.this.log.isWarnEnabled()) {
							PersonalLooperBuilder.this.log.warn(e.toString());
							e.printStackTrace();
						}
					}
				}
			} finally {
				PersonalLooperBuilder.this.resourceCounter.decrement(PersonalLooperBuilder.this.resource);
			}
		}

		private void getAndFeed() throws Exception {
			Protocol protocol = (Protocol) future.get(PersonalLooperBuilder.this.interval.getInterval(), PersonalLooperBuilder.this.interval.getUnit());
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
