package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Input {

	public Boolean input(JIDContext context, Protocol protocol);

	public interface InputFinder {

		public Input find(Protocol protocol);
	}

	public interface InputCondition {

		public Input getInput();

		public InputMatcher getMatcher();
	}

	public interface InputMatcher {

		public Boolean match(Protocol protocol);
	}
}
