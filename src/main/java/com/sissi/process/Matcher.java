package com.sissi.process;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-4
 */
public interface Matcher {

	public Boolean match(Protocol protocol);

	public final static class AllMatcher implements Matcher {

		@Override
		public Boolean match(Protocol protocol) {
			return true;
		}
	}
}
