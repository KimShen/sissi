package com.sissi.process.iq;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-11-4
 */
public class IQMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return IQ.class.isAssignableFrom(protocol.getClass());
	}
}
