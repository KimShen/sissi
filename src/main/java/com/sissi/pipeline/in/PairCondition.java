package com.sissi.pipeline.in;

import com.sissi.pipeline.Input;
import com.sissi.pipeline.Input.InputCondition;
import com.sissi.pipeline.Input.InputMatcher;

/**
 * @author kim 2013-11-21
 */
public class PairCondition implements InputCondition {

	private final Input input;

	private final InputMatcher matcher;

	public PairCondition(Input input, InputMatcher matcher) {
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