package com.sissi.read.sax;

<<<<<<< HEAD
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
=======
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author kim 2013-10-21
 */
public class SAXFuture implements Future<Object> {

<<<<<<< HEAD
	private BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(20);
=======
	private BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
>>>>>>> 838666326a5f8bf3770663eab3e45807f83c2dc3

	public boolean set(Object node) {
		return this.queue.offer(node);
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	public boolean isCancelled() {
		return false;
	}

	public boolean isDone() {
		return !this.queue.isEmpty();
	}

	public Object get() throws InterruptedException, ExecutionException {
		return this.queue.poll();
	}

	public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return this.queue.poll(timeout, unit);
	}
}
