package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final GenericFutureListener<Future<Void>> futureListener;

	private ChannelHandlerContext context;

	public NetworkTransfer(ChannelHandlerContext context) {
		this(FailLogedGenericFutureListener.FUTURE, context);
	}

	public NetworkTransfer(GenericFutureListener<Future<Void>> futureListener, ChannelHandlerContext context) {
		super();
		this.futureListener = futureListener;
		this.context = context;
	}

	@Override
	public NetworkTransfer transfer(TransferBuffer buffer) {
		this.context.writeAndFlush(ByteBuf.class.cast(buffer.getBuffer())).addListener(new ReleaseGenericFutureListener(buffer)).addListener(this.futureListener);
		return this;
	}

	@Override
	public void close() {
		if (this.context != null) {
			this.context.close().addListener(FailLogedGenericFutureListener.FUTURE);
		}
	}

	private class ReleaseGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private final TransferBuffer transferBuffer;

		public ReleaseGenericFutureListener(TransferBuffer transferBuffer) {
			super();
			this.transferBuffer = transferBuffer;
		}

		public void operationComplete(Future<Void> future) throws Exception {
			this.transferBuffer.release();
		}
	}

	private static class FailLogedGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private final static GenericFutureListener<Future<Void>> FUTURE = new FailLogedGenericFutureListener();

		private final Log log = LogFactory.getLog(this.getClass());

		private FailLogedGenericFutureListener() {
			super();
		}

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				Trace.trace(this.log, future.cause());
			}
		}
	}
}
