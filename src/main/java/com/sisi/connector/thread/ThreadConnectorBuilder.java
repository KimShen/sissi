package com.sisi.connector.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.FactoryBean;

import com.sisi.connector.Connector;
import com.sisi.connector.ConnectorBuilder;
import com.sisi.connector.thread.ThreadConnector.Interval;
import com.sisi.connector.thread.ThreadConnector.Runner;
import com.sisi.feed.Feeder;

/**
 * @author kim 2013-10-30
 */
public class ThreadConnectorBuilder implements ConnectorBuilder, FactoryBean<ThreadConnectorBuilder> {

	private Executor executor;

	private Integer threadNum;

	private Interval interval;

	public ThreadConnectorBuilder(int corePoolSize, int maximumPoolSize, long keepAliveTime, Integer threadNum, Interval interval) {
		super();
		this.threadNum = threadNum;
		this.interval = interval;
		this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	}

	@Override
	public Connector builder(Future<?> future, Feeder feeder) {
		return new ThreadConnector(new Runner(this.executor, this.threadNum), interval, future, feeder);
	}

	@Override
	public ThreadConnectorBuilder getObject() throws Exception {
		return this;
	}

	@Override
	public Class<ConnectorBuilder> getObjectType() {
		return ConnectorBuilder.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
