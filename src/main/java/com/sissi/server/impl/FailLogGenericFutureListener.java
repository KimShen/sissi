package com.sissi.server.impl;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kim 2013年12月1日
 */
public class FailLogGenericFutureListener implements GenericFutureListener<Future<Void>> {

	public final static GenericFutureListener<Future<Void>> INSTANCE = new FailLogGenericFutureListener();

	private Log log = LogFactory.getLog(this.getClass());

	private FailLogGenericFutureListener() {
		super();
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (!future.isSuccess()) {
			this.logIfDetail(future.cause());
		}
	}

	private void logIfDetail(Throwable cause) {
		StringWriter trace = new StringWriter();
		cause.printStackTrace(new PrintWriter(trace));
		this.log.error(trace.toString());
	}
}