package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.muc.Owner;

public class MucOwnerSetActionMatcher extends ClassMatcher {

	private final XDataType type;

	public MucOwnerSetActionMatcher(String action) {
		super(Owner.class);
		this.type = XDataType.parse(action);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Owner.class).getX().type(this.type);
	}
}
