package com.sissi.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.Context;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Processor {

	public void process(Context context, Protocol current);

	public final static class NothingProcessor implements Processor {

		public final static Processor NOTHING = new NothingProcessor();

		private Log log = LogFactory.getLog(this.getClass());

		@Override
		public void process(Context context, Protocol current) {
			this.log.warn("In nothing processor for " + current + ", please check");
		}
	}
}
