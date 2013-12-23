package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月1日
 */
public class NetworkTransfer implements Transfer {

	private final GenericFutureListener<Future<Void>> futureListener;

	private final ChannelHandlerContext context;

	public NetworkTransfer(GenericFutureListener<Future<Void>> futureListener, ChannelHandlerContext context) {
		super();
		this.futureListener = futureListener;
		this.context = context;
	}

	public ByteBuf allocBuffer() {
		return this.context.alloc().buffer();
	}

	@Override
	public Transfer transfer(ByteBuf bytebuf) {
		this.context.writeAndFlush(bytebuf).addListener(this.futureListener).addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}

	@Override
	public Transfer close() {
		this.context.close().addListener(FailLogGenericFutureListener.INSTANCE);
		return this;
	}

	private static class FailLogGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public final static GenericFutureListener<Future<Void>> INSTANCE = new FailLogGenericFutureListener();

		private final Log log = LogFactory.getLog(this.getClass());

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
}
