package com.sissi.looper.impl;

import java.util.concurrent.Future;

import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.protocol.Protocol;
import com.sissi.thread.impl.ExecuteInterval;

/**
 * @author kim 2014年4月14日
 */
abstract class FeedLooper implements Looper {

	private final ExecuteInterval interval;

	private final Future<?> future;

	private final Feeder feeder;

	FeedLooper(ExecuteInterval interval, Future<?> future, Feeder feeder) {
		super();
		this.interval = interval;
		this.future = future;
		this.feeder = feeder;
	}

	/**
	 * 获取或堵塞
	 * 
	 * @throws Exception
	 */
	protected void getAndFeed() throws Exception {
		Protocol protocol = Protocol.class.cast(this.future.get(this.interval.getInterval(), this.interval.getUnit()));
		if (protocol != null) {
			this.feeder.feed(protocol);
		}
	}
}
