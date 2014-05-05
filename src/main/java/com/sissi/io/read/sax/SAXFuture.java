package com.sissi.io.read.sax;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author kim 2013-10-21
 */
public class SAXFuture implements Future<Object> {

	private final BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();

	private final byte limit;

	/**
	 * 队列限制
	 * 
	 * @param limit
	 */
	public SAXFuture(byte limit) {
		super();
		this.limit = limit;
	}

	public boolean push(Object node) {
		// Ignore policy
		if (this.queue.size() < this.limit) {
			this.queue.offer(node);
			return true;
		}
		return false;
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	public boolean isCancelled() {
		return false;
	}

	public boolean isDone() {
		return false;
	}

	public Object get() throws InterruptedException, ExecutionException {
		return this.queue.poll();
	}

	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.queue.poll(timeout, unit);
	}
}
