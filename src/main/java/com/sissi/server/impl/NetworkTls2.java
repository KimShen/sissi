package com.sissi.server.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLEngine;

import com.sissi.server.ServerTls2;
import com.sissi.server.ServerTlsBuilder;

/**
 * @author kim 2013年12月17日
 */
public class NetworkTls2 implements ServerTls2, GenericFutureListener<Future<Void>> {

	private final AtomicBoolean isStarttls = new AtomicBoolean();

	private final AtomicBoolean prepareStarttls = new AtomicBoolean();

	private final ServerTlsBuilder serverTlsbuilder;
    
	private final ChannelHandlerContext context;

	private SslHandler handler;

	public NetworkTls2(ServerTlsBuilder serverTlsbuilder, ChannelHandlerContext context) {
		super();
		this.context = context;
		this.serverTlsbuilder = serverTlsbuilder;
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (future.isSuccess() && this.prepareStarttls.get()) {
			this.context.pipeline().addFirst(this.handler);
			this.prepareStarttls.compareAndSet(true, false);
		}
	}

	@Override
	public NetworkTls2 starttls() {
		if (this.isStarttls.compareAndSet(false, true)) {
			SSLEngine engine = this.serverTlsbuilder.getSSLContext().createSSLEngine();
			engine.setNeedClientAuth(false);
			engine.setUseClientMode(false);
			this.handler = new SslHandler(engine);
			this.prepareStarttls.compareAndSet(false, true);
		}
		return this;
	}

	public Boolean isTls() {
		return this.isStarttls.get();
	}
	
}
