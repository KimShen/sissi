package com.sissi.pipeline;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-16
 */
public interface InputCondition {

	public Input getInput();

	public InputMatcher getMatcher();

	public interface InputMatcher {

		public Boolean match(Protocol protocol);
	}

	public interface InputFinder {

		public Input find(Protocol protocol);
	}
}
