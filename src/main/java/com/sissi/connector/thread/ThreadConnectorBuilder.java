package com.sissi.connector.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.beans.factory.FactoryBean;

import com.sissi.connector.Connector;
import com.sissi.connector.ConnectorBuilder;
import com.sissi.connector.thread.ThreadConnector.Interval;
import com.sissi.connector.thread.ThreadConnector.Runner;
import com.sissi.feed.Feeder;

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
