package com.sissi.server.exchange.impl;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sissi.commons.Extracter;
import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.config.MongoConfig;
import com.sissi.gc.GC;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Exchanger;
import com.sissi.server.exchange.ExchangerContext;
import com.sissi.server.exchange.ExchangerTerminal;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;
import com.sissi.write.Transfer;
import com.sissi.write.TransferBuffer;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final String fieldHost = "host";

	private final String fieldDate = "date";

	private final Log log = LogFactory.getLog(this.getClass());

	private final Map<String, Exchanger> exchangers = new ConcurrentHashMap<String, Exchanger>();

	private final DBObject filter = BasicDBObjectBuilder.start(this.fieldHost, 1).get();

	private final MongoConfig config;

	private final long timeout;

	public BridgeExchangerContext(long timeout, Runner runner, Interval interval, MongoConfig config, ResourceCounter resourceCounter) {
		super();
		this.timeout = timeout;
		this.config = config.clear();
		runner.executor(1, new LeakGC(interval, resourceCounter));
		runner.executor(1, new TimeoutGC(interval, resourceCounter));
	}

	@Override
	public Exchanger join(String host, Transfer transfer) {
		BridgeExchanger exchanger = new BridgeExchanger(host, transfer);
		this.exchangers.put(host, exchanger);
		this.config.collection().save(this.build(host, true));
		return exchanger;
	}

	@Override
	public Exchanger active(String host) {
		Exchanger exchanger = this.exchangers.remove(host);
		this.config.collection().remove(this.build(host, false));
		return exchanger;
	}

	@Override
	public boolean exists(String host) {
		return this.config.collection().findOne(this.build(host, false)) != null;
	}

	private DBObject build(String host, boolean date) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start(this.fieldHost, host);
		if (date) {
			builder.add(this.fieldDate, System.currentTimeMillis());
		}
		return builder.get();
	}

	private class LeakGC extends GC {

		public LeakGC(Interval interval, ResourceCounter resourceCounter) {
			super(interval, LeakGC.class, resourceCounter);
		}

		@Override
		public boolean gc() {
			for (String host : BridgeExchangerContext.this.exchangers.keySet()) {
				if (!BridgeExchangerContext.this.exists(host)) {
					BridgeExchangerContext.this.exchangers.get(host).close(ExchangerTerminal.TARGET).close(ExchangerTerminal.SOURCE);
					BridgeExchangerContext.this.log.warn("Find leak exchanger: " + host);
				}
			}
			return true;
		}
	}

	private class TimeoutGC extends GC {

		public TimeoutGC(Interval interval, ResourceCounter resourceCounter) {
			super(interval, TimeoutGC.class, resourceCounter);
		}

		@Override
		public boolean gc() {
			DBCursor cursor = BridgeExchangerContext.this.config.collection().find(BasicDBObjectBuilder.start(BridgeExchangerContext.this.fieldDate, BasicDBObjectBuilder.start("$gt", System.currentTimeMillis() - BridgeExchangerContext.this.timeout).get()).get(), BridgeExchangerContext.this.filter);
			try {
				while (cursor.hasNext()) {
					Exchanger exchanger = BridgeExchangerContext.this.active(Extracter.asString(DBObject.class.cast(cursor.next()), BridgeExchangerContext.this.fieldHost));
					// Double check for multi thread
					if (exchanger != null) {
						BridgeExchangerContext.this.log.warn("Find leak context: " + exchanger.host());
						exchanger.close(ExchangerTerminal.SOURCE);
						exchanger.close(ExchangerTerminal.TARGET);
					}
				}
			} finally {
				cursor.close();
			}
			return true;
		}
	}

	private class BridgeExchanger implements Exchanger {

		private final Transfer target;

		private final String host;

		private Closeable source;

		private BridgeExchanger(String host, Transfer target) {
			this.target = target;
			this.host = host;
		}

		public String host() {
			return this.host;
		}

		public BridgeExchanger source(Closeable source) {
			this.source = source;
			return this;
		}

		@Override
		public BridgeExchanger write(TransferBuffer buffer) {
			this.target.transfer(buffer);
			return this;
		}

		@Override
		public BridgeExchanger close(ExchangerTerminal closer) {
			return closer == ExchangerTerminal.SOURCE ? this.close(this.source) : this.close(this.target);
		}

		private BridgeExchanger close(Closeable closer) {
			try {
				IOUtils.closeQuietly(closer);
			} catch (Exception e) {
				BridgeExchangerContext.this.log.debug(e.toString());
				Trace.trace(BridgeExchangerContext.this.log, e);
			}
			return this;
		}
	}
}
