package com.sissi.gc;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Interval;
import com.sissi.resource.ResourceMonitor;

/**
 * @author kim 2014年1月15日
 */
abstract public class GC implements Runnable {
	
	private final static Log log = LogFactory.getLog(GC.class);

	private final Long sleep;

	private final ResourceMonitor resourceMonitor;

	protected GC(Interval interval, ResourceMonitor resourceMonitor) {
		super();
		this.sleep = TimeUnit.MILLISECONDS.convert(interval.getInterval(), interval.getUnit());
		this.resourceMonitor = resourceMonitor;
	}

	protected Long getSleep() {
		return sleep;
	}

	@Override
	public void run() {
		try {
			this.resourceMonitor.increment();
			while (true) {
				try {
					this.gc();
					Thread.sleep(this.getSleep());
				} catch (Exception e) {
					if (log.isErrorEnabled()) {
						log.error(e.toString());
						e.printStackTrace();
					}
				}
			}
		} finally {
			this.resourceMonitor.decrement();
		}
	}

	abstract protected void gc();
}