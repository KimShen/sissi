package com.sissi.server.impl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final GenericFutureListener<Future<Void>> futureListener;

	private ChannelHandlerContext context;

	public NetworkTransfer(ChannelHandlerContext context) {
		super();
		this.futureListener = FailLogedGenericFutureListener.INSTANCE;
		this.context = context;
	}

	public NetworkTransfer(GenericFutureListener<Future<Void>> futureListener, ChannelHandlerContext context) {
		super();
		this.futureListener = futureListener;
		this.context = context;
	}

	@Override
	public Transfer transfer(ByteBuffer bytebuf) {
		this.context.writeAndFlush(Unpooled.wrappedBuffer(bytebuf)).addListener(this.futureListener);
		return this;
	}

	@Override
	public void close() {
		this.context.close().addListener(FailLogedGenericFutureListener.INSTANCE);
		this.context = null;
	}

	private static class FailLogedGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public final static GenericFutureListener<Future<Void>> INSTANCE = new FailLogedGenericFutureListener();

		private final Log log = LogFactory.getLog(this.getClass());

		private FailLogedGenericFutureListener() {
			super();
		}

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				if (this.log.isDebugEnabled()) {
					this.logIfDetail(future.cause());
				}
			}
		}

		private void logIfDetail(Throwable cause) {
			StringWriter trace = new StringWriter();
			cause.printStackTrace(new PrintWriter(trace));
			this.log.debug(trace.toString());
		}
	}
}
