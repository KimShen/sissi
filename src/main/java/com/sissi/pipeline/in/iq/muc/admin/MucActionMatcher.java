package com.sissi.pipeline.in.iq.muc.admin;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.MucMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XMucAdminAction;

/**
 * <query xmlns='http://jabber.org/protocol/muc#admin'><item item='xxx'/affiliation='xxxx'/></query>
 * 
 * @author kim 2014年3月14日
 */
public class MucActionMatcher extends MucMatcher {

	private final XMucAdminAction action;

	private final ProtocolType type;

	public MucActionMatcher(JIDBuilder jidBuilder, String action, String type) {
		super(XMucAdmin.class, jidBuilder);
		this.type = ProtocolType.parse(type);
		this.action = XMucAdminAction.parse(action);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.parent().type(this.type) && protocol.cast(XMucAdmin.class).item(this.action);
	}
}
