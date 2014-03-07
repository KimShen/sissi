package com.sissi.ucenter.history.impl;

/**
 * @author kim 2014年3月6日
 */
public class DefaultParams {

	private final int limit;

	private final long since;

	public DefaultParams(int limit, long since) {
		super();
		this.limit = limit;
		this.since = since;
	}

	public int limit() {
		return this.limit;
	}

	public long since() {
		return System.currentTimeMillis() - this.since;
	}
}
