package com.sissi.gc;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.resource.ResourceCounter;
import com.sissi.thread.Interval;

/**
 * @author kim 2014年1月15日
 */
abstract public class GC implements Runnable {

	private final static Log log = LogFactory.getLog(GC.class);

	private final long sleep;

	private final String resource;

	private final ResourceCounter resourceCounter;

	protected GC(Interval interval, Class<? extends GC> resource, ResourceCounter resourceCounter) {
		super();
		this.sleep = interval.convert(TimeUnit.MILLISECONDS);
		this.resource = resource.getSimpleName();
		this.resourceCounter = resourceCounter;
	}

	@Override
	public void run() {
		try {
			this.resourceCounter.increment(this.resource);
			while (true) {
				try {
				//	if (this.gc()) {
						Thread.sleep(this.sleep);
					// } else {
					// break;
					// }
				} catch (Exception e) {
					log.error(e.toString());
					Trace.trace(log, e);
				}
			}
		} finally {
			this.resourceCounter.decrement(this.resource);
		}
	}

	abstract protected boolean gc();
}