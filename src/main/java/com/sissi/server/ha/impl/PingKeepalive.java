package com.sissi.server.ha.impl;

import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.commons.thread.Interval;
import com.sissi.commons.thread.Runner;
import com.sissi.context.JIDContext;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.ping.Ping;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.ha.Keepalive;

/**
 * Ping/Pong
 * 
 * @author kim 2014年1月8日
 */
public class PingKeepalive implements Keepalive, Runnable {

	private final Log log = LogFactory.getLog(this.getClass());

	private final String resource = PingTimeout.class.getSimpleName();

	private final DelayQueue<PingTimeout> timeouts = new DelayQueue<PingTimeout>();

	private final long interval;

	private final ResourceCounter resourceCounter;

	public PingKeepalive(Runner runner, Interval interval, ResourceCounter resourceCounter) {
		super();
		this.resourceCounter = resourceCounter;
		this.interval = interval.convert(TimeUnit.MILLISECONDS);
		runner.executor(1, this);
	}

	@Override
	public PingKeepalive ping(JIDContext context) {
		String uid = UUID.randomUUID().toString();
		this.timeouts.offer(new PingTimeout(context.ping(uid.hashCode()).write(new IQ().setId(uid).add(Ping.PING).setType(ProtocolType.GET), true)));
		return this;
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
			this.deadline = PingKeepalive.this.interval + System.currentTimeMillis();
			PingKeepalive.this.resourceCounter.increment(PingKeepalive.this.resource);
		}

		public PingTimeout timeout() {
			this.context.closeTimeout();
			PingKeepalive.this.resourceCounter.decrement(PingKeepalive.this.resource);
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
