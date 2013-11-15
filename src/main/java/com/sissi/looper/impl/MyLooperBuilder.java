package com.sissi.looper.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.beans.factory.FactoryBean;

import com.sissi.feed.Feeder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.looper.impl.MyLooper.Interval;
import com.sissi.looper.impl.MyLooper.Runner;

/**
 * @author kim 2013-10-30
 */
public class MyLooperBuilder implements LooperBuilder, FactoryBean<MyLooperBuilder> {

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
}
