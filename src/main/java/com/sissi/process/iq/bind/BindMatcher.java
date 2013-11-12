package com.sissi.process.iq.bind;

import com.sissi.process.Matcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bind.Bind;

/**
 * @author kim 2013-11-4
 */
public class BindMatcher implements Matcher {

	@Override
	public Boolean match(Protocol protocol) {
		return Bind.class.isAssignableFrom(protocol.getClass());
	}
}
