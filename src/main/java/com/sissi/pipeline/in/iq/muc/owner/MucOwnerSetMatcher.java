package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.muc.Owner;

/**
 * <query xmlns='http://jabber.org/protocol/muc#owner'><x xmlns='jabber:x:data' type='Xxx(如submit)'>...</x></query>
 * 
 * @author kim 2014年4月19日
 */
public class MucOwnerSetMatcher extends ClassMatcher {

	private final XDataType type;

	public MucOwnerSetMatcher(String action) {
		super(Owner.class);
		this.type = XDataType.parse(action);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Owner.class).getX().type(this.type);
	}
}
