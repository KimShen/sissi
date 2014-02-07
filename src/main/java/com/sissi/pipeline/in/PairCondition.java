package com.sissi.pipeline.in;

import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputCondition;
import com.sissi.pipeline.InputMatcher;

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

	public Input input() {
		return this.input;
	}

	public InputMatcher matcher() {
		return this.matcher;
	}
}