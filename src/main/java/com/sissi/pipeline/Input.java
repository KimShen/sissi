package com.sissi.pipeline;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Input {

	public boolean input(JIDContext context, Protocol protocol);

	public class NothingProcessor implements Input {

		public final static Input NOTHING = new NothingProcessor();

		private Log log = LogFactory.getLog(this.getClass());

		private NothingProcessor() {

		}

		@Override
		public boolean input(JIDContext context, Protocol current) {
			this.log.warn("Nothing for " + current + ", please check");
			return false;
		}
	}
}
