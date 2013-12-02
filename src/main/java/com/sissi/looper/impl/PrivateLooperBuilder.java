package com.sissi.looper.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.Looper.LooperBuilder;
import com.sissi.protocol.Protocol;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;

/**
 * @author kim 2013-10-30
 */
public class PrivateLooperBuilder implements LooperBuilder, FactoryBean<PrivateLooperBuilder> {

	private final static Log LOG = LogFactory.getLog(PrivateLooperBuilder.class);

	private final Runner runner;

	private final Interval interval;

	private final Integer threadNum;

	public PrivateLooperBuilder(Runner runner, Interval interval, Integer threadNum) {
		super();
		this.runner = runner;
		this.interval = interval;
		this.threadNum = threadNum;
	}

	@Override
	public Looper build(Future<?> future, Feeder feeder) {
		return new PrivateLooper(future, feeder);
	}

	@Override
	public PrivateLooperBuilder getObject() throws Exception {
		return this;
	}

	@Override
	public Class<LooperBuilder> getObjectType() {
		return LooperBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private class PrivateLooper implements Runnable, Looper {

		private final AtomicBoolean state;

		private final AtomicInteger counter;

		private final Future<?> future;

		private final Feeder feeder;

		private PrivateLooper(Future<?> future, Feeder feeder) {
			super();
			this.state = new AtomicBoolean();
			this.counter = new AtomicInteger();
			this.future = future;
			this.feeder = feeder;
		}

		@Override
		public void run() {
			this.counter.incrementAndGet();
			while (true) {
				try {
					if (this.prepareStop()) {
						LOG.debug("Looper is stopping, current is " + this.counter.get());
						break;
					}
					this.getAndFeed();
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			this.counter.decrementAndGet();
		}

		private void getAndFeed() throws InterruptedException, ExecutionException, TimeoutException {
			Protocol protocol = (Protocol) future.get(PrivateLooperBuilder.this.interval.getInterval(), PrivateLooperBuilder.this.interval.getUnit());
			if (protocol != null) {
				this.feeder.feed(protocol);
			}
		}

		private boolean prepareStop() {
			return this.state.get() == false;
		}

		@Override
		public void start() {
			this.state.set(true);
			PrivateLooperBuilder.this.runner.executor(PrivateLooperBuilder.this.threadNum, this);
			LOG.debug("Looper is running, current is " + this.counter.get());
		}

		@Override
		public void stop() {
			this.state.set(false);
		}
	}
}
