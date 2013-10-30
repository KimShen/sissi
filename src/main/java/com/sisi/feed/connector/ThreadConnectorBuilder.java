package com.sisi.feed.connector;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.FactoryBean;

import com.sisi.feed.Connector;
import com.sisi.feed.ConnectorBuilder;
import com.sisi.feed.Feeder;

/**
 * @author kim 2013-10-30
 */
public class ThreadConnectorBuilder implements ConnectorBuilder, FactoryBean<ThreadConnectorBuilder> {

	private Executor executor;

	private Integer threadNum;

	public ThreadConnectorBuilder(int corePoolSize, int maximumPoolSize, long keepAliveTime, Integer threadNum) {
		super();
		this.threadNum = threadNum;
		this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	}

	@Override
	public Connector builder(Future<Object> future, Feeder feeder) {
		return new ThreadConnector(this.executor, this.threadNum, future, feeder);
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
