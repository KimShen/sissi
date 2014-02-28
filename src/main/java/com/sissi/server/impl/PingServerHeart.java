package com.sissi.server.impl;

import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JIDContext;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.ping.Ping;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.ServerHeart;
import com.sissi.thread.Interval;
import com.sissi.thread.Runner;

/**
 * @author kim 2014年1月8日
 */
public class PingServerHeart implements ServerHeart, Runnable {

	private final int timeoutThreadNumber = 1;

	private final Log log = LogFactory.getLog(this.getClass());

	private final String resource = PingTimeout.class.getSimpleName();

	private final DelayQueue<PingTimeout> timeouts = new DelayQueue<PingTimeout>();

	private final long interval;

	private final ResourceCounter resourceCounter;

	public PingServerHeart(Runner runner, Interval interval, ResourceCounter resourceCounter) {
		super();
		this.resourceCounter = resourceCounter;
		this.interval = interval.convert(TimeUnit.MILLISECONDS);
		runner.executor(this.timeoutThreadNumber, this);
	}

	@Override
	public String ping(JIDContext context) {
		String uid = UUID.randomUUID().toString();
		this.timeouts.add(new PingTimeout(context));
		context.write(new IQ().setId(uid).add(Ping.PING).setType(ProtocolType.GET), true);
		return uid;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.timeouts.take().timeout();
			} catch (Exception e) {
				this.log.warn(e.toString());
				Trace.trace(this.log, e);
			}
		}
	}

	private class PingTimeout implements Delayed {

		private final JIDContext context;

		private final long deadline;

		public PingTimeout(JIDContext context) {
			this.context = context;
			this.deadline = PingServerHeart.this.interval + System.currentTimeMillis();
			PingServerHeart.this.resourceCounter.increment(PingServerHeart.this.resource);
		}

		public PingTimeout timeout() {
			this.context.closeTimeout();
			PingServerHeart.this.resourceCounter.decrement(PingServerHeart.this.resource);
			return this;
		}

		public long getDelay(TimeUnit unit) {
			return unit.convert(this.deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		public int compareTo(Delayed o) {
			return o.getDelay(TimeUnit.MILLISECONDS) >= this.getDelay(TimeUnit.MILLISECONDS) ? 1 : -1;
		}
	}
}
