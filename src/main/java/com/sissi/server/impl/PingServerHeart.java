package com.sissi.server.impl;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Interval;
import com.sissi.commons.Runner;
import com.sissi.context.JIDContext;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.ping.Ping;
import com.sissi.resource.ResourceMonitor;
import com.sissi.server.ServerHeart;

/**
 * @author kim 2014年1月8日
 */
public class PingServerHeart implements ServerHeart, Runnable {

	private final Integer timeoutThreads = 1;

	private final AtomicLong eids = new AtomicLong();

	private final DelayQueue<PingTimeout> timeouts = new DelayQueue<PingTimeout>();

	private final Log log = LogFactory.getLog(this.getClass());

	private final Interval interval;

	private final ResourceMonitor resourceMonitor;

	public PingServerHeart(Runner runner, Interval interval, ResourceMonitor resourceMonitor) {
		super();
		this.interval = interval;
		this.resourceMonitor = resourceMonitor;
		runner.executor(this.timeoutThreads, this);
	}

	@Override
	public Long ping(JIDContext context) {
		Long eid = this.eids.incrementAndGet();
		this.timeouts.add(new PingTimeout(context));
		context.write(new IQ().setId(eid).add(Ping.PING).setType(ProtocolType.GET));
		return eid;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.timeouts.take().gc();
			} catch (Exception e) {
				if (this.log.isWarnEnabled()) {
					this.log.warn(e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	private class PingTimeout implements Delayed {

		private final JIDContext context;

		private final Long deadline;

		public PingTimeout(JIDContext context) {
			this.context = context;
			this.deadline = TimeUnit.MILLISECONDS.convert(PingServerHeart.this.interval.getInterval(), PingServerHeart.this.interval.getUnit()) + System.currentTimeMillis();
			PingServerHeart.this.resourceMonitor.increment();
		}

		public PingTimeout gc() {
			this.context.closeTimeout();
			PingServerHeart.this.resourceMonitor.decrement();
			return this;
		}

		public long getDelay(TimeUnit unit) {
			return unit.convert(deadline - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		public int compareTo(Delayed o) {
			return o.getDelay(TimeUnit.MILLISECONDS) >= this.getDelay(TimeUnit.MILLISECONDS) ? 1 : -1;
		}
	}
}
