package com.sissi.looper.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-30
 */
public class MyLooperBuilder implements LooperBuilder, FactoryBean<MyLooperBuilder> {

	private final static Log LOG = LogFactory.getLog(MyLooperBuilder.class);

	public static class Interval {

		private Long interval;

		private TimeUnit unit;

		public Interval(Long interval, TimeUnit unit) {
			super();
			this.interval = interval;
			this.unit = unit;
		}

		public Long getInterval() {
			return interval;
		}

		public TimeUnit getUnit() {
			return unit;
		}
	}

	private static class Runner {

		private Executor executor;

		private Integer threadNum;

		public Runner(Executor executor, Integer threadNum) {
			super();
			this.executor = executor;
			this.threadNum = threadNum;
		}

		public void executor(Runnable runnable) {
			LOG.debug("ThreadConnector will open " + threadNum + " threads");
			for (int num = 0; num < threadNum; num++) {
				this.executor.execute(runnable);
			}
		}
	}

	private Executor executor;

	private Integer threadNum;

	private Interval interval;

	public MyLooperBuilder(Executor executor, Integer threadNum, Interval interval) {
		super();
		this.threadNum = threadNum;
		this.interval = interval;
		this.executor = executor;
	}

	@Override
	public Looper build(Future<?> future, Feeder feeder) {
		return new MyLooper(new Runner(this.executor, this.threadNum), this.interval, future, feeder);
	}

	@Override
	public MyLooperBuilder getObject() throws Exception {
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

	private static class MyLooper implements Runnable, Looper {

		private final AtomicBoolean state;

		private final AtomicInteger counter;

		private final Runner runner;

		private final Future<?> future;

		private final Feeder feeder;

		private final Interval interval;

		private MyLooper(Runner runner, Interval interval, Future<?> future, Feeder feeder) {
			super();
			this.state = new AtomicBoolean();
			this.counter = new AtomicInteger();
			this.runner = runner;
			this.future = future;
			this.feeder = feeder;
			this.interval = interval;
		}

		@Override
		public void run() {
			this.counter.incrementAndGet();
			while (true) {
				try {
					if (this.prepareStop()) {
						LOG.debug("MyLooper is stopping");
						break;
					}
					this.getAndFeed();
				} catch (Exception e) {
					LOG.error(e);
					if (LOG.isTraceEnabled()) {
						throw new RuntimeException(e);
					}
				}
			}
			this.counter.decrementAndGet();
		}

		private void getAndFeed() throws InterruptedException, ExecutionException, TimeoutException {
			Protocol protocol = (Protocol) future.get(interval.getInterval(), interval.getUnit());
			if (protocol != null) {
				LOG.debug("Get Protocol: " + protocol);
				this.feeder.feed(protocol);
			}
		}

		private boolean prepareStop() {
			return this.state.get() == false;
		}

		@Override
		public void start() {
			this.state.set(true);
			this.runner.executor(this);
			LOG.debug("MyLooper is running");
		}

		@Override
		public void stop() {
			this.state.set(false);
		}
	}
}
