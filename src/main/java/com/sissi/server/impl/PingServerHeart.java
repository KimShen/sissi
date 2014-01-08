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
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.ping.Ping;
import com.sissi.server.ServerHeart;

/**
 * @author kim 2014年1月8日
 */
public class PingServerHeart implements ServerHeart, Runnable {

	private final Integer GC_THREAD = 1;
	
	private final AtomicLong eids = new AtomicLong();

	private final DelayQueue<GC> gcs = new DelayQueue<GC>();
	
	private final Log log = LogFactory.getLog(this.getClass());

	private final Interval interval;

	public PingServerHeart(Runner runner, Interval interval) {
		super();
		this.interval = interval;
		runner.executor(GC_THREAD, this);
	}

	@Override
	public Long ping(JIDContext context) {
		Long eid = this.eids.incrementAndGet();
		this.gcs.add(new GC(context, this.interval));
		context.write(new IQ().add(Ping.PING).setType(Type.GET).setId(eid));
		return eid;
	}

	@Override
	public void run() {
		for (;;) {
			try {
				this.gcs.take().gc();
			} catch (Exception e) {
				if (this.log.isWarnEnabled()) {
					this.log.warn(e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	private class GC implements Delayed {

		private final JIDContext context;

		private Long deadline;

		public GC(JIDContext context, Interval interval) {
			this.context = context;
			this.deadline = TimeUnit.MILLISECONDS.convert(interval.getInterval(), interval.getUnit()) + System.currentTimeMillis();
		}

		public GC gc() {
			this.context.closeTimeout();
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
