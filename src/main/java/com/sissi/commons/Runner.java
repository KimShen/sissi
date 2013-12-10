package com.sissi.commons;

import java.util.concurrent.Executor;

/**
 * @author kim 2013年11月28日
 */
public class Runner {

	private final Executor executor;

	public Runner(Executor executor) {
		super();
		this.executor = executor;
	}

	public void executor(Integer threadNum, Runnable runnable) {
		for (int num = 0; num < threadNum; num++) {
			this.executor.execute(runnable);
		}
	}
}