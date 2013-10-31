package com.sisi.connector.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.connector.Connector;
import com.sisi.feed.Feeder;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ThreadConnector implements Runnable, Connector {

	private final static Log LOG = LogFactory.getLog(ThreadConnector.class);

	private final AtomicBoolean state = new AtomicBoolean();

	private Runner runner;

	private Future<?> future;

	private Feeder feeder;

	private Interval interval;

	public ThreadConnector(Runner runner, Interval interval, Future<?> future, Feeder feeder) {
		super();
		this.runner = runner;
		this.future = future;
		this.feeder = feeder;
		this.interval = interval;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (this.prepareStop()) {
					break;
				}
				this.getAndFeed();
			} catch (Exception e) {
				LOG.error(e);
				throw new RuntimeException(e);
			}
		}
		LOG.info(Thread.currentThread().getId() + " would be closed");
	}

	private void getAndFeed() throws InterruptedException, ExecutionException, TimeoutException {
		Protocol protocol = (Protocol) future.get(interval.getInterval(), interval.getUnit());
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
		this.runner.executor(this);
	}

	@Override
	public void stop() {
		this.state.set(false);
	}

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

	public static class Runner {

		private Executor executor;

		private Integer threadNum;

		public Runner(Executor executor, Integer threadNum) {
			super();
			this.executor = executor;
			this.threadNum = threadNum;
		}

		public Executor getExecutor() {
			return executor;
		}

		public Integer getThreadNum() {
			return threadNum;
		}

		public void executor(Runnable runnable) {
			for (int num = 0; num < threadNum; num++) {
				this.executor.execute(runnable);
			}
		}
	}
}