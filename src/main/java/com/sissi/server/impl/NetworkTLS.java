package com.sissi.server.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLEngine;

import com.sissi.server.ServerTls;
import com.sissi.server.ServerTlsBuilder;

/**
 * @author kim 2013年12月17日
 */
public class NetworkTls implements ServerTls, GenericFutureListener<Future<Void>> {

	private final AtomicBoolean isStarttls = new AtomicBoolean();

	private final AtomicBoolean openStarttls = new AtomicBoolean();

	private final ServerTlsBuilder serverTlsbuilder;

	private final ChannelHandlerContext context;

	private SSLEngine engine;

	public NetworkTls(ServerTlsBuilder serverTlsbuilder, ChannelHandlerContext context) {
		super();
		this.context = context;
		this.serverTlsbuilder = serverTlsbuilder;
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (future.isSuccess() && this.openStarttls.get()) {
			this.context.pipeline().addFirst(new SslHandler(engine));
			this.isStarttls.compareAndSet(false, true);
			this.openStarttls.compareAndSet(true, false);
		}
	}

	@Override
	public NetworkTls starttls() {
		if (this.openStarttls.compareAndSet(false, true)) {
			this.engine = this.serverTlsbuilder.getSSLContext().createSSLEngine();
			this.engine.setNeedClientAuth(false);
			this.engine.setUseClientMode(false);
		}
		return this;
	}

	public Boolean isUsing() {
		return this.isStarttls.get();
	}
}
