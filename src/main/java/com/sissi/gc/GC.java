package com.sissi.gc;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Interval;
import com.sissi.resource.ResourceCounter;

/**
 * @author kim 2014年1月15日
 */
abstract public class GC implements Runnable {

	private final static Log LOG = LogFactory.getLog(GC.class);

	private final Long sleep;

	private final ResourceCounter resourceCounter;

	protected GC(Interval interval, ResourceCounter resourceCounter) {
		super();
		this.sleep = interval.convert(TimeUnit.MILLISECONDS);
		this.resourceCounter = resourceCounter;
	}

	@Override
	public void run() {
		try {
			this.resourceCounter.increment();
			while (true) {
				try {
					if (this.gc()) {
						Thread.sleep(this.sleep);
					} else {
						break;
					}
				} catch (Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(e.toString());
						e.printStackTrace();
					}
				}
			}
		} finally {
			this.resourceCounter.decrement();
		}
	}

	abstract protected Boolean gc();
}