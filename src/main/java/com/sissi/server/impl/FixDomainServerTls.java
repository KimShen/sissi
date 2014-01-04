package com.sissi.server.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLEngine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.server.ServerTls;
import com.sissi.server.ServerTlsBuilder;

/**
 * @author kim 2013年12月17日
 */
public class FixDomainServerTls implements ServerTls, GenericFutureListener<Future<Void>> {

	private final Log log = LogFactory.getLog(this.getClass());

	private final AtomicBoolean isStarttls = new AtomicBoolean();

	private final AtomicBoolean prepareStarttls = new AtomicBoolean();

	private final ServerTlsBuilder serverTlsbuilder;

	private final ChannelHandlerContext context;

	private SslHandler handler;

	public FixDomainServerTls(ServerTlsBuilder serverTlsbuilder, ChannelHandlerContext context) {
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
	public Boolean startTls(String domain) {
		try {
			return this.prepareSSL();
		} catch (Exception e) {
			if (this.log.isErrorEnabled()) {
				this.log.error(e.toString());
				e.printStackTrace();
			}
			return this.rollbackSSL();
		}
	}

	public Boolean isTls(String domain) {
		return this.isStarttls.get();
	}

	private Boolean rollbackSSL() {
		this.isStarttls.set(false);
		this.prepareStarttls.set(false);
		return false;
	}

	private Boolean prepareSSL() {
		if (this.isStarttls.compareAndSet(false, true)) {
			SSLEngine engine = this.serverTlsbuilder.getSSLContext().createSSLEngine();
			engine.setNeedClientAuth(false);
			engine.setUseClientMode(false);
			this.handler = new SslHandler(engine);
			this.prepareStarttls.compareAndSet(false, true);
		}
		return true;
	}
}
