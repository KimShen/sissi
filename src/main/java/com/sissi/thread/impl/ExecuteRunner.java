package com.sissi.thread.impl;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.thread.Runner;

/**
 * @author kim 2013年11月28日
 */
public class ExecuteRunner implements Runner {

	private final Log log = LogFactory.getLog(this.getClass());

	private final Executor executor;

	public ExecuteRunner(Executor executor) {
		super();
		this.executor = executor;
	}

	public void executor(int threadNum, Runnable runnable) {
		for (int num = 0; num < threadNum; num++) {
			this.executor.execute(runnable);
		}
		this.log.debug("Executor " + threadNum + " for " + runnable.getClass());
	}
}