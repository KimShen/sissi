package com.sissi.netty.listener;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kim 2013-10-26
 */
public class FailLogGenericFutureListener implements GenericFutureListener<Future<Void>> {
	
	public final static GenericFutureListener<Future<Void>> INSTANCE = new FailLogGenericFutureListener();

	private Log log = LogFactory.getLog(this.getClass());

	private FailLogGenericFutureListener() {
		super();
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (!future.isSuccess()) {
			this.log.error(future.cause());
		}
	}
}
