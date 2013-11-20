package com.sissi.pipeline.in;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.pipeline.Input;
import com.sissi.pipeline.Input.NothingProcessor;
import com.sissi.pipeline.InputCondition;
import com.sissi.pipeline.InputCondition.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ChainedFinder implements InputFinder {

	private final Log log = LogFactory.getLog(this.getClass());

	private List<InputCondition> conditions;

	public ChainedFinder(List<InputCondition> conditions) {
		super();
		this.conditions = conditions;
	}

	@Override
	public Input find(Protocol protocol) {
		for (InputCondition each : this.conditions) {
			if (each.getMatcher().match(protocol)) {
				this.log.debug("Protocol " + protocol.getClass() + " will use " + each.getClass());
				return each.getInput();
			}
		}
		this.log.warn("None Input can process " + protocol.getClass());
		return NothingProcessor.NOTHING;
	}

	public static class BeanCondition implements InputCondition {

		private Input input;

		private InputMatcher matcher;

		public BeanCondition(Input input, InputMatcher matcher) {
			super();
			this.input = input;
			this.matcher = matcher;
		}

		public Input getInput() {
			return input;
		}

		public InputMatcher getMatcher() {
			return matcher;
		}
	}
}
