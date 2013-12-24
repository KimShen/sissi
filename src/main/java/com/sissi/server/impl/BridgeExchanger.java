package com.sissi.server.impl;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.sissi.server.Exchanger;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchanger implements Exchanger {

	private final ChannelHandlerContext target;

	private Closeable initer;

	public BridgeExchanger(ChannelHandlerContext target) throws IOException {
		this.target = target;
	}

	public BridgeExchanger initer(Closeable initer) {
		this.initer = initer;
		return this;
	}

	@Override
	public Exchanger write(ByteBuffer bytes) {
		this.target.writeAndFlush(Unpooled.wrappedBuffer(bytes));
		return this;
	}

	@Override
	public Exchanger close() {
		this.target.close().addListener(new BridgeCloseListener());
		return this;
	}

	private class BridgeCloseListener implements GenericFutureListener<Future<Void>> {

		@Override
		public void operationComplete(Future<Void> future) throws Exception {
			if (future.isSuccess()) {
				BridgeExchanger.this.initer.close();
			}
		}
	}
}
