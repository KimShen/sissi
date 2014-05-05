package com.sissi.looper.impl;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.thread.Interval;
import com.sissi.commons.thread.Runner;
import com.sissi.feed.Feeder;
import com.sissi.looper.LooperBuilder;
import com.sissi.resource.ResourceCounter;

/**
 * 私有Loop策略,每个Loop独享私有线程
 * 
 * @author kim 2013-10-30
 */
public class PersonalLooperBuilder implements LooperBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final String resource = PersonalLooper.class.getSimpleName();

	private final ResourceCounter resourceCounter;

	private final Interval interval;

	private final Runner runner;

	private final int threadNum;

	/**
	 * @param runner
	 * @param interval 堵塞时间
	 * @param threadNum 线程数量
	 * @param resourceCounter
	 */
	public PersonalLooperBuilder(Runner runner, Interval interval, int threadNum, ResourceCounter resourceCounter) {
		super();
		this.runner = runner;
		this.interval = interval;
		this.threadNum = threadNum;
		this.resourceCounter = resourceCounter;
	}

	@Override
	public PersonalLooper build(Future<?> future, Feeder feeder) {
		return new PersonalLooper(future, feeder);
	}

	private class PersonalLooper extends FeedLooper implements Runnable {

		private final AtomicBoolean state = new AtomicBoolean();

		private PersonalLooper(Future<?> future, Feeder feeder) {
			super(PersonalLooperBuilder.this.interval, future, feeder);
		}

		@Override
		public void run() {
			// 私有Loop开启计数
			PersonalLooperBuilder.this.resourceCounter.increment(PersonalLooperBuilder.this.resource);
			while (true) {
				try {
					if (this.prepareStop()) {
						break;
					}
					super.getAndFeed();
				} catch (Exception e) {
					PersonalLooperBuilder.this.log.warn(e.toString());
					Trace.trace(PersonalLooperBuilder.this.log, e);
				}
			}
			// 私有Loop关闭计数
			PersonalLooperBuilder.this.resourceCounter.decrement(PersonalLooperBuilder.this.resource);
		}

		/**
		 * 是否关闭私有Loop
		 * 
		 * @return
		 */
		private boolean prepareStop() {
			return this.state.get() == false;
		}

		/*
		 * 开启指定线程数量的私有Loop
		 * 
		 * @see com.sissi.looper.Looper#start()
		 */
		@Override
		public PersonalLooper start() {
			this.state.set(true);
			PersonalLooperBuilder.this.runner.executor(PersonalLooperBuilder.this.threadNum, this);
			return this;
		}

		/*
		 * 关闭所有关联此私有Loop的线程, 如果线程正在堵塞则Interval间隔后关闭
		 * 
		 * @see com.sissi.looper.Looper#stop()
		 */
		@Override
		public PersonalLooper stop() {
			this.state.set(false);
			return this;
		}
	}
}
