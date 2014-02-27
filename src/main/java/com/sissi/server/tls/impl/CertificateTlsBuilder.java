package com.sissi.server.tls.impl;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.server.tls.ServerTlsBuilder;

/**
 * @author kim 2013年12月18日
 */
public class CertificateTlsBuilder implements ServerTlsBuilder {

	private final String protocol = "TLS";

	private final String keystore = "jks";

	private final Log log = LogFactory.getLog(this.getClass());

	private final SSLContext context;

	public CertificateTlsBuilder(Certificate key, Certificate trust) {
		super();
		this.context = this.build(key, trust);
	}

	@Override
	public SSLContext getSSLContext() {
		return this.context;
	}

	private SSLContext build(Certificate key, Certificate trust) {
		try {
			SSLContext context = SSLContext.getInstance(this.protocol);
			context.init(this.getKeyManagers(key), this.getTrustManagers(trust), null);
			return context;
		} catch (Exception e) {
			this.log.fatal(e.toString());
			Trace.trace(this.log, e);
			return null;
		}
	}

	private KeyManager[] getKeyManagers(Certificate key) throws Exception {
		KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		InputStream certificate = key.getFile().openStream();
		try {
			KeyStore ks = KeyStore.getInstance(this.keystore);
			ks.load(certificate, key.getPassword());
			factory.init(ks, key.getPassword());
		} finally {
			IOUtils.closeQuietly(certificate);
		}
		return factory.getKeyManagers();
	}

	private TrustManager[] getTrustManagers(Certificate trust) throws Exception {
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		InputStream certificate = trust.getFile().openStream();
		try {
			KeyStore ks = KeyStore.getInstance(this.keystore);
			ks.load(certificate, trust.getPassword());
			factory.init(ks);
		} finally {
			IOUtils.closeQuietly(certificate);
		}
		return factory.getTrustManagers();
	}
}
