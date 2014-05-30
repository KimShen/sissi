package com.sissi.looper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.thread.Interval;
import com.sissi.commons.thread.Runner;
import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.resource.ResourceCounter;

/**
 * 公共Loop策略,所有Loop共享公共线程
 * 
 * @author kim 2014年4月14日
 */
public class GlobalLooperBuilder implements Looper, LooperBuilder, Runnable {

	private final String resource = GlobalLooperBuilder.class.getSimpleName();

	/**
	 * TODO: 优化随机删除
	 */
	private final List<GlobalLooper> loopers = new ArrayList<GlobalLooper>();

	private final Random random = new Random(new Date().getTime());

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicBoolean state = new AtomicBoolean();

	/**
	 * 计数器
	 */
	private final AtomicLong next = new AtomicLong();

	private final ResourceCounter resourceCounter;

	private final Interval interval;

	private final int threadNum;

	/**
	 * @param runner
	 * @param interval 堵塞时间
	 * @param threadNum 线程数量
	 * @param resourceCounter
	 */
	public GlobalLooperBuilder(Runner runner, Interval interval, int threadNum, ResourceCounter resourceCounter) {
		super();
		this.start();
		this.interval = interval;
		this.threadNum = threadNum;
		this.resourceCounter = resourceCounter;
		runner.executor(threadNum, this);
	}

	@Override
	public GlobalLooper build(Future<?> future, Feeder feeder) {
		return new GlobalLooper(future, feeder);
	}

	@Override
	public GlobalLooperBuilder start() {
		this.state.set(true);
		return this;
	}

	@Override
	public GlobalLooperBuilder stop() {
		this.state.set(false);
		return this;
	}

	@Override
	public void run() {
		this.resourceCounter.increment(this.resource);
		while (true) {
			try {
				if (!this.state.get()) {
					break;
				}
				int mod = this.loopers.size();
				if (mod > 0) {
					this.getAndFeed(mod);
				} else {
					// TODO: change to wait();
					Thread.sleep(this.interval.convert(TimeUnit.SECONDS) * 1000 * this.random.nextInt(this.threadNum));
				}
			} catch (Exception e) {
				this.log.warn(e.toString());
				Trace.trace(this.log, e);
			}
		}
		this.resourceCounter.decrement(this.resource);
	}

	/**
	 * 取值范围为顺序0-looper.size()
	 * 
	 * @param mod
	 * @throws Exception
	 */
	private void getAndFeed(int mod) throws Exception {
		GlobalLooper looper = this.loopers.get((int) (this.next.incrementAndGet() % mod));
		// Double check
		if (looper != null) {
			looper.getAndFeed();
		}
	}

	private class GlobalLooper extends FeedLooper {

		public GlobalLooper(Future<?> future, Feeder feeder) {
			super(GlobalLooperBuilder.this.interval, future, feeder);
		}

		@Override
		public GlobalLooper start() {
			GlobalLooperBuilder.this.loopers.add(this);
			return this;
		}

		@Override
		public GlobalLooper stop() {
			GlobalLooperBuilder.this.loopers.remove(this);
			return this;
		}
	}
}
