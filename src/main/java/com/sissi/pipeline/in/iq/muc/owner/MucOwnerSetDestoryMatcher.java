package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Owner;

/**
 * @author kim 2014年3月30日
 */
public class MucOwnerSetDestoryMatcher extends ClassMatcher {

	public MucOwnerSetDestoryMatcher() {
		super(Owner.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Owner.class).destory();
	}
}
