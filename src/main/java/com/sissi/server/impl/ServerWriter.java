package com.sissi.server.impl;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author kim 2013年12月17日
 */
public class ServerWriter implements GenericFutureListener<Future<Void>> {

	public void operationComplete(Future<Void> future) throws Exception {
		if (!future.isSuccess()) {
		}
	}
}