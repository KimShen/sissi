package com.sissi.pipeline.in;

import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputCondition;

/**
 * @author kim 2013-11-21
 */
public class BeanCondition implements InputCondition {

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