package com.sissi.server.impl;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sissi.commons.IOUtils;
import com.sissi.server.Exchanger;
import com.sissi.server.ExchangerContext;
import com.sissi.write.Transfer;

/**
 * @author kim 2013年12月22日
 */
public class BridgeExchangerContext implements ExchangerContext {

	protected final Map<String, Exchanger> cached = new ConcurrentHashMap<String, Exchanger>();

	@Override
	public Exchanger set(String host, Transfer transfer) {
		BridgeExchanger exchanger = new BridgeExchanger(host, transfer);
		this.cached.put(host, exchanger);
		return exchanger;
	}

	@Override
	public Exchanger get(String host) {
		return this.cached.remove(host);
	}

	@Override
	public Boolean isTarget(String host) {
		return !this.cached.containsKey(host);
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
		public Exchanger write(ByteBuffer bytes) {
			this.target.transfer(bytes);
			return this;
		}

		public Exchanger close(Closeable closer) {
			BridgeExchangerContext.this.cached.remove(this.host);
			IOUtils.closeQuietly(closer);
			return this;
		}

		@Override
		public Exchanger closeIniter() {
			this.close(this.initer);
			return this;
		}

		@Override
		public Exchanger closeTarget() {
			this.close(this.target);
			return this;
		}
	}
}
