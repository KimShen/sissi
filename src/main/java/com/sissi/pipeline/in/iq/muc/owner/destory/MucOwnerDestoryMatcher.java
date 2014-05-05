package com.sissi.pipeline.in.iq.muc.owner.destory;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Owner;

/**
 * <query xmlns='http://jabber.org/protocol/muc#owner'><destroy jid='darkcave@chat.shakespeare.lit'><reason>Macbeth doth come.</reason></destroy></query>
 * 
 * @author kim 2014年3月30日
 */
public class MucOwnerDestoryMatcher extends ClassMatcher {

	public MucOwnerDestoryMatcher() {
		super(Owner.class);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Owner.class).destory();
	}
}
