package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputCondition;
import com.sissi.pipeline.InputFinder;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public class ChainedFinder implements InputFinder {

	private final List<InputCondition> conditions;

	public ChainedFinder(List<InputCondition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Input find(Protocol protocol) {
		for (InputCondition each : this.conditions) {
			if (each.getMatcher().match(protocol)) {
				return each.getInput();
			}
		}
		return NothingProcessor.NOTHING;
	}
}
