package com.sissi.server.tls.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLEngine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.server.tls.ServerTls;
import com.sissi.server.tls.ServerTlsBuilder;

/**
 * @author kim 2013年12月17日
 */
public class FixDomainServerTls implements ServerTls, GenericFutureListener<Future<Void>> {

	private final static Log log = LogFactory.getLog(FixDomainServerTls.class);

	private final AtomicBoolean prepareTls = new AtomicBoolean();

	private final AtomicBoolean isTls = new AtomicBoolean();

	private final ServerTlsBuilder serverTlsBuilder;

	private final ChannelHandlerContext context;

	private SslHandler handler;

	public FixDomainServerTls(ServerTlsBuilder serverTlsBuilder, ChannelHandlerContext context) {
		super();
		this.context = context;
		this.serverTlsBuilder = serverTlsBuilder;
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (future.isSuccess() && this.prepareTls.get()) {
			this.context.pipeline().addFirst(this.handler);
			this.prepareTls.compareAndSet(true, false);
		}
	}

	@Override
	public boolean startTls(String domain) {
		try {
			return this.prepareSSL();
		} catch (Exception e) {
			log.error(e.toString());
			Trace.trace(log, e);
			return this.rollbackSSL();
		}
	}

	public boolean isTls(String domain) {
		return this.isTls.get();
	}

	private boolean rollbackSSL() {
		this.isTls.set(false);
		this.prepareTls.set(false);
		return false;
	}

	private boolean prepareSSL() {
		if (this.isTls.compareAndSet(false, true)) {
			SSLEngine engine = this.serverTlsBuilder.getSSLContext().createSSLEngine();
			engine.setNeedClientAuth(false);
			engine.setUseClientMode(false);
			this.handler = new SslHandler(engine);
			this.prepareTls.compareAndSet(false, true);
		}
		return true;
	}
}
