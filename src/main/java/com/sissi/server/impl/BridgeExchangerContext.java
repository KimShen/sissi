package com.sissi.server.impl;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.commons.apache.IOUtils;
import com.sissi.config.MongoConfig;
import com.sissi.gc.GC;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerCloser;
import com.sissi.server.ExchangerContext;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final Integer gcThreads = 1;

	private final Log log = LogFactory.getLog(this.getClass());

	private final Map<String, Exchanger> cached = new ConcurrentHashMap<String, Exchanger>();

	private final MongoConfig config;

	public BridgeExchangerContext(Runner runner, Interval interval, MongoConfig config, ResourceCounter resourceCounter) {
		super();
		this.config = config.clear();
		runner.executor(this.gcThreads, new LeakGC(interval, resourceCounter));
	}

	@Override
	public Exchanger set(String host, Transfer transfer) {
		BridgeExchanger exchanger = new BridgeExchanger(host, transfer);
		this.config.collection().save(this.build(host));
		this.cached.put(host, exchanger);
		return exchanger;
	}

	@Override
	public Exchanger get(String host) {
		return this.remove(host);
	}

	private Exchanger remove(String host) {
		this.config.collection().remove(this.build(host));
		return this.cached.remove(host);
	}

	@Override
	public Boolean isTarget(String host) {
		return !this.exists(host);
	}

	private DBObject build(String host) {
		return BasicDBObjectBuilder.start().add("host", host).get();
	}

	private Boolean exists(String host) {
		return this.config.collection().findOne(this.build(host)) != null;
	}

	private class LeakGC extends GC {

		public LeakGC(Interval interval, ResourceCounter resourceCounter) {
			super(interval, LeakGC.class, resourceCounter);
		}

		@Override
		public Boolean gc() {
			for (String host : BridgeExchangerContext.this.cached.keySet()) {
				if (!BridgeExchangerContext.this.exists(host)) {
					Exchanger leak = BridgeExchangerContext.this.cached.get(host);
					leak.close(ExchangerCloser.TARGET);
					leak.close(ExchangerCloser.INITER);
					BridgeExchangerContext.this.log.error("Find leak exchanger: " + host);
				}
			}
			return true;
		}
	}

	private class BridgeExchanger implements Exchanger {

		private final String host;

		private final Transfer target;

		private Closeable initer;

		private BridgeExchanger(String host, Transfer target) {
			this.host = host;
			this.target = target;
		}

		public BridgeExchanger initer(Closeable initer) {
			this.initer = initer;
			return this;
		}

		@Override
		public Exchanger write(TransferBuffer buffer) {
			this.target.transfer(buffer);
			return this;
		}

		public Exchanger close(Closeable closer) {
			try {
				IOUtils.closeQuietly(closer);
				BridgeExchangerContext.this.remove(this.host);
			} catch (Exception e) {
				if (BridgeExchangerContext.this.log.isDebugEnabled()) {
					BridgeExchangerContext.this.log.debug(e.toString());
				}
			}
			return this;
		}

		@Override
		public Exchanger close(ExchangerCloser closer) {
			return closer == ExchangerCloser.INITER ? this.close(this.initer) : this.close(this.target);
		}
	}
}
