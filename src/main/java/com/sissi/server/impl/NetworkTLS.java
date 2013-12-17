package com.sissi.server.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.sissi.server.ServerTLS;

/**
 * @author kim 2013年12月17日
 */
public class NetworkTLS implements ServerTLS, GenericFutureListener<Future<Void>> {

	private final AtomicBoolean needStarttls = new AtomicBoolean();
	
	private final AtomicBoolean isStarttls = new AtomicBoolean();

	private final ChannelHandlerContext context;

	private final SSLEngine engine;

	public NetworkTLS(ChannelHandlerContext context) {
		super();
		this.context = context;
		this.engine = getContext().createSSLEngine();
		this.engine.setNeedClientAuth(false);
		this.engine.setUseClientMode(false);
	}

	public void operationComplete(Future<Void> future) throws Exception {
		if (future.isSuccess() && this.needStarttls.get() && !this.isStarttls()) {
			this.context.pipeline().addFirst(new SslHandler(engine));
			this.isStarttls.compareAndSet(false, true);
		}
	}

	@Override
	public NetworkTLS starttls() {
		this.needStarttls.compareAndSet(false, true);
		return this;
	}

	public Boolean isStarttls(){
		return this.isStarttls.get();
	}
	
	protected static SSLContext getContext() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(getKeyManagers(), getTrustManagers(), null);
			return context;
		} catch (Exception e) {
			return null;
		}
	}

	protected static KeyManager[] getKeyManagers() throws IOException, GeneralSecurityException {
		String alg = KeyManagerFactory.getDefaultAlgorithm();
		KeyManagerFactory kmFact = KeyManagerFactory.getInstance(alg);
		FileInputStream fis = new FileInputStream("client.store");
		KeyStore ks = KeyStore.getInstance("jks");
		ks.load(fis, "client".toCharArray());
		fis.close();
		kmFact.init(ks, "client".toCharArray());
		return kmFact.getKeyManagers();
	}

	protected static TrustManager[] getTrustManagers() throws IOException, GeneralSecurityException {
		String alg = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmFact = TrustManagerFactory.getInstance(alg);
		FileInputStream fis = new FileInputStream("client.store");
		KeyStore ks = KeyStore.getInstance("jks");
		ks.load(fis, "client".toCharArray());
		fis.close();
		tmFact.init(ks);
		return tmFact.getTrustManagers();
	}

	public class TLSGenericFutureListener implements GenericFutureListener<Future<Void>> {

		public void operationComplete(Future<Void> future) throws Exception {

			if (future.isSuccess()) {

			}
		}
	}
}
