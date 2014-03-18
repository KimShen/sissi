package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.protocol.muc.XMucAdminAction;

/**
 * @author kim 2014年3月14日
 */
public class MucSetActionMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final XMucAdminAction action;

	public MucSetActionMatcher(JIDBuilder jidBuilder, String action) {
		super(XMucAdmin.class);
		this.jidBuilder = jidBuilder;
		this.action = XMucAdminAction.parse(action);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup() && protocol.parent().type(ProtocolType.SET) && protocol.cast(XMucAdmin.class).item(this.action);
	}
}
