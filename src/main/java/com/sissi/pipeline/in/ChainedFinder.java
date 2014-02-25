package com.sissi.pipeline.in;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputCondition;
import com.sissi.pipeline.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ChainedFinder implements InputFinder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final Input nothing = new NothingProcessor();

	private final List<InputCondition> conditions;

	public ChainedFinder(List<InputCondition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Input find(Protocol protocol) {
		for (InputCondition each : this.conditions) {
			if (each.matcher().match(protocol)) {
				Input input = each.input();
				this.log.debug("Input " + input.getClass() + "for " + protocol.getClass());
				return each.input();
			}
		}
		return this.nothing;
	}

	private class NothingProcessor implements Input {

		private final Log log = LogFactory.getLog(this.getClass());

		private NothingProcessor() {

		}

		@Override
		public boolean input(JIDContext context, Protocol current) {
			this.log.warn("Nothing for " + current.getClass() + ", please check");
			return false;
		}
	}
}
