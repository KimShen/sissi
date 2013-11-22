package com.sissi.pipeline.in.iq;

import com.sissi.pipeline.in.MatchClass;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-11-19
 */
public class IQResultMatcher extends MatchClass {

	public IQResultMatcher() {
		super(IQ.class);
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol.getParent()) && this.isNotLoop(protocol);
	}

	private boolean isNotLoop(Protocol protocol) {
		return Type.SET.equals(protocol.getParent().getType()) || protocol.getParent().getType() == null;
	}
}
