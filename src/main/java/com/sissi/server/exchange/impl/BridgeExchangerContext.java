package com.sissi.server.exchange.impl;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.commons.thread.Interval;
import com.sissi.commons.thread.Runner;
import com.sissi.config.Dictionary;
import com.sissi.config.MongoConfig;
import com.sissi.config.impl.MongoUtils;
import com.sissi.pipeline.Transfer;
import com.sissi.pipeline.TransferBuffer;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.exchange.Exchanger;
import com.sissi.server.exchange.ExchangerContext;
import com.sissi.server.exchange.Terminal;

/**
 * 索引策略: {"host":1},{"date":1}
 * 
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	private final String date = "date";

	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * {"host":1}
	 */
	private final DBObject filter = BasicDBObjectBuilder.start(Dictionary.FIELD_HOST, 1).get();

	private final Map<String, Exchanger> exchangers = new ConcurrentHashMap<String, Exchanger>();

	private final ResourceCounter resourceCounter;

	private final MongoConfig config;

	private final Interval interval;

	private final long timeout;

	public BridgeExchangerContext(long timeout, Runner runner, Interval interval, MongoConfig config, ResourceCounter resourceCounter) {
		super();
		this.timeout = timeout;
		this.interval = interval;
		this.config = config.reset();
		this.resourceCounter = resourceCounter;
		runner.executor(1, new Timeout());
	}

	@Override
	public Exchanger wait(String host, boolean cascade, Transfer transfer) {
		BridgeExchanger exchanger = new BridgeExchanger(host, cascade, transfer);
		if (MongoUtils.effect(this.config.collection().save(this.build(host, true), WriteConcern.SAFE))) {
			this.exchangers.put(host, exchanger);
		}
		return exchanger;
	}

	@Override
	public Exchanger activate(String host) {
		if (MongoUtils.effect(this.config.collection().remove(this.build(host, false), WriteConcern.SAFE))) {
			// Double check 4 multi thread
			Exchanger exchanger = this.exchangers.remove(host);
			return exchanger != null ? exchanger : new NothingExchanger(host);
		}
		return new NothingExchanger(host);
	}

	@Override
	public boolean exists(String host) {
		return this.config.collection().findOne(this.build(host, false)) != null;
	}

	/**
	 * {"host":host,"date":Xxx}
	 * 
	 * @param host
	 * @param date
	 * @return
	 */
	private DBObject build(String host, boolean date) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start(Dictionary.FIELD_HOST, host);
		if (date) {
			builder.add(this.date, System.currentTimeMillis());
		}
		return builder.get();
	}

	/**
	 * 终止发起方在超时时间内尚未激活的Exchanger
	 * 
	 * @author kim 2014年4月20日
	 */
	private class Timeout implements Runnable {

		private final long sleep = BridgeExchangerContext.this.interval.convert(TimeUnit.MILLISECONDS);

		private final String resource = Timeout.class.getSimpleName();

		@Override
		public void run() {
			try {
				BridgeExchangerContext.this.resourceCounter.increment(this.resource);
				while (true) {
					try {
						this.timeout();
						Thread.sleep(this.sleep);
					} catch (Exception e) {
						log.error(e.toString());
						Trace.trace(log, e);
					}
				}
			} finally {
				BridgeExchangerContext.this.resourceCounter.decrement(this.resource);
			}
		}

		private Timeout timeout() {
			try (DBCursor cursor = BridgeExchangerContext.this.config.collection().find(BasicDBObjectBuilder.start(BridgeExchangerContext.this.date, BasicDBObjectBuilder.start("$lt", System.currentTimeMillis() - BridgeExchangerContext.this.timeout).get()).get(), BridgeExchangerContext.this.filter)) {
				while (cursor.hasNext()) {
					Exchanger exchanger = BridgeExchangerContext.this.activate(MongoUtils.asString(DBObject.class.cast(cursor.next()), Dictionary.FIELD_HOST));
					// Double check 4 multi thread
					if (exchanger != null) {
						BridgeExchangerContext.this.log.warn("Timeout socks: " + exchanger.host());
						exchanger.close(Terminal.ALL);
					}
				}
			}
			return this;
		}
	}

	private class BridgeExchanger implements Exchanger {

		private final Transfer target;

		/**
		 * 是否允许关闭Target
		 */
		private final boolean cascade;

		private final String host;

		private Closeable source;

		/**
		 * @param host
		 * @param cascade 是否允许关闭Target
		 * @param target 接收方
		 */
		private BridgeExchanger(String host, boolean cascade, Transfer target) {
			this.host = host;
			this.cascade = cascade;
			this.target = target;
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
		public BridgeExchanger close(Terminal terminal) {
			switch (terminal) {
			case ALL:
				return this.close(Terminal.SOURCE).close(Terminal.TARGET);
			case SOURCE:
				return this.close(this.source);
			case TARGET:
				return this.cascade ? this.close(this.target) : this;
			}
			return this;
		}

		/**
		 * 无论是否成功都将隐式激活(Activate)
		 * 
		 * @param closer
		 * @return
		 */
		private BridgeExchanger close(Closeable closer) {
			try {
				IOUtils.closeQuietly(closer);
			} catch (Exception e) {
				BridgeExchangerContext.this.log.warn(e.toString());
				Trace.trace(BridgeExchangerContext.this.log, e);
			} finally {
				BridgeExchangerContext.this.activate(this.host);
			}
			return this;
		}
	}

	private final class NothingExchanger implements Exchanger {

		private final String host;

		public NothingExchanger(String host) {
			super();
			this.host = host;
		}

		@Override
		public String host() {
			return this.host;
		}

		@Override
		public NothingExchanger source(Closeable source) {
			return this;
		}

		@Override
		public NothingExchanger write(TransferBuffer buffer) {
			BridgeExchangerContext.this.log.info("Nothing on " + this.host + ", please check");
			return this;
		}

		@Override
		public NothingExchanger close(Terminal terminal) {
			return this;
		}
	}
}
