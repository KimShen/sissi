package com.sissi.looper.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

import com.sissi.feed.Feeder;
import com.sissi.looper.impl.MyLooper;
import com.sissi.looper.impl.MyLooper.Interval;
import com.sissi.looper.impl.MyLooper.Runner;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class MyLooperTest {

	private final static Long WAIT_FOR_SHUTDOWN = 100L;

	@Test
	public void testCorrectThreads() throws Exception {
		ThreadPoolExecutor exe = new ThreadPoolExecutor(4, 600, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
		Runner runner = new Runner(exe, 4);
		Interval interval = new Interval(1000L, TimeUnit.MILLISECONDS);
		Feeder feeder = new NoneFeeder();
		Future<?> future = (Future<?>) EasyMock.createMock(Future.class);
		EasyMock.expect(future.get()).andReturn(null).anyTimes();
		EasyMock.expect(future.get(EasyMock.anyLong(), EasyMock.anyObject(TimeUnit.class))).andReturn(null).anyTimes();
		EasyMock.replay(future);
		MyLooper looper = new MyLooper(runner, interval, future, feeder);
		looper.start();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(4, exe.getActiveCount());
		looper.stop();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(0, exe.getActiveCount());
	}
	
	@Test
	public void testCorrectThreadsWithMulti() throws Exception {
		ThreadPoolExecutor exe = new ThreadPoolExecutor(20, 600, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
		Runner runner = new Runner(exe, 4);
		Interval interval = new Interval(1000L, TimeUnit.MILLISECONDS);
		Feeder feeder = new NoneFeeder();
		Future<?> future = (Future<?>) EasyMock.createMock(Future.class);
		EasyMock.expect(future.get()).andReturn(null).anyTimes();
		EasyMock.expect(future.get(EasyMock.anyLong(), EasyMock.anyObject(TimeUnit.class))).andReturn(null).anyTimes();
		EasyMock.replay(future);
		MyLooper looper1 = new MyLooper(runner, interval, future, feeder);
		MyLooper looper2 = new MyLooper(runner, interval, future, feeder);
		looper1.start();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(4, exe.getActiveCount());
		looper2.start();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(8, exe.getActiveCount());
		looper1.stop();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(4, exe.getActiveCount());
		looper2.stop();
		Thread.sleep(WAIT_FOR_SHUTDOWN);
		Assert.assertEquals(0, exe.getActiveCount());
	}

	private static class NoneFeeder implements Feeder {

		@Override
		public void feed(Protocol protocol) {

		}
	}
}