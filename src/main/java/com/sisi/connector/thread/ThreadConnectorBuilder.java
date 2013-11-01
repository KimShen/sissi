package com.sisi.connector.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

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

	public ThreadConnectorBuilder(Executor executor, Integer threadNum, Interval interval) {
		super();
		this.threadNum = threadNum;
		this.interval = interval;
		this.executor = executor;
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
