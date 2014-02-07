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
import com.sissi.server.ExchangerContext;
import com.sissi.server.ExchangerPoint;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final int gcThreadNumber = 1;

	private final String fieldHost = "host";

	private final Log log = LogFactory.getLog(this.getClass());

	private final Map<String, Exchanger> exchangers = new ConcurrentHashMap<String, Exchanger>();

	private final MongoConfig config;

	public BridgeExchangerContext(Runner runner, Interval interval, MongoConfig config, ResourceCounter resourceCounter) {
		super();
		this.config = config.clear();
		runner.executor(this.gcThreadNumber, new LeakGC(interval, resourceCounter));
	}

	@Override
	public Exchanger join(String host, Transfer transfer) {
		BridgeExchanger exchanger = new BridgeExchanger(transfer);
		this.exchangers.put(host, exchanger);
		this.config.collection().save(this.build(host));
		return exchanger;
	}

	@Override
	public Exchanger leave(String host) {
		Exchanger exchanger = this.exchangers.remove(host);
		this.config.collection().remove(this.build(host));
		return exchanger;
	}

	@Override
	public boolean exists(String host) {
		return this.config.collection().findOne(this.build(host)) != null;
	}

	private DBObject build(String host) {
		return BasicDBObjectBuilder.start(this.fieldHost, host).get();
	}

	private class LeakGC extends GC {

		public LeakGC(Interval interval, ResourceCounter resourceCounter) {
			super(interval, LeakGC.class, resourceCounter);
		}

		@Override
		public boolean gc() {
			for (String host : BridgeExchangerContext.this.exchangers.keySet()) {
				if (!BridgeExchangerContext.this.exists(host)) {
					BridgeExchangerContext.this.exchangers.get(host).close(ExchangerPoint.TARGET).close(ExchangerPoint.SOURCE);
					BridgeExchangerContext.this.log.warn("Find leak exchanger: " + host);
				}
			}
			return true;
		}
	}

	private class BridgeExchanger implements Exchanger {

		private final Transfer target;

		private Closeable source;

		private BridgeExchanger(Transfer target) {
			this.target = target;
		}

		public BridgeExchanger source(Closeable source) {
			this.source = source;
			return this;
		}

		@Override
		public Exchanger write(TransferBuffer buffer) {
			this.target.transfer(buffer);
			return this;
		}

		@Override
		public Exchanger close(ExchangerPoint closer) {
			return closer == ExchangerPoint.SOURCE ? this.close(this.source) : this.close(this.target);
		}

		private Exchanger close(Closeable closer) {
			try {
				IOUtils.closeQuietly(closer);
			} catch (Exception e) {
				if (BridgeExchangerContext.this.log.isDebugEnabled()) {
					BridgeExchangerContext.this.log.debug(e.toString());
				}
			}
			return this;
		}
	}
}
