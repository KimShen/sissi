package com.sissi.looper;

import java.util.concurrent.Future;

import com.sissi.feed.Feeder;

/**
 * @author kim 2013-10-30
 */
public interface Looper {

	public void start();

	public void stop();

	public interface LooperBuilder {

		public Looper build(Future<?> future, Feeder feeder);
	}
}
