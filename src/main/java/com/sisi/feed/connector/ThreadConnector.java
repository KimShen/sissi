package com.sisi.feed.connector;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.feed.Connector;
import com.sisi.feed.Feeder;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class ThreadConnector implements Runnable, Connector {

	private final static Log LOG = LogFactory.getLog(ThreadConnector.class);

	private final AtomicBoolean state = new AtomicBoolean();

	private Executor executor;

	private Integer threadNum;

	private Future<Object> future;

	private Feeder feeder;

	public ThreadConnector(Executor executor, Integer threadNum, Future<Object> future, Feeder feeder) {
		super();
		this.executor = executor;
		this.threadNum = threadNum;
		this.future = future;
		this.feeder = feeder;
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (this.state.get() == false) {
					break;
				}

				Protocol protocol = (Protocol) future.get(5000, TimeUnit.MILLISECONDS);
				if (protocol != null) {
					this.feeder.feed(protocol);
				}
			} catch (Exception e) {
				LOG.error(e);
				throw new RuntimeException(e);
			}
		}
		LOG.info(Thread.currentThread().getId() + " would be closed");
	}

	@Override
	public void start() {
		this.state.set(true);
		for (int num = 0; num < threadNum; num++) {
			this.executor.execute(this);
		}
	}

	@Override
	public void stop() {
		this.state.set(false);
	}
}