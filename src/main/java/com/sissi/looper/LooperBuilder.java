package com.sissi.looper;

import java.util.concurrent.Future;

import com.sissi.feed.Feeder;

/**
 * @author kim 2013年12月23日
 */
public interface LooperBuilder {

	public Looper build(Future<?> future, Feeder feeder);
}