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
public class MucActionMatcher extends ClassMatcher {

	private final XMucAdminAction action;

	private final JIDBuilder jidBuilder;

	private final ProtocolType type;

	public MucActionMatcher(JIDBuilder jidBuilder, String action, String type) {
		super(XMucAdmin.class);
		this.jidBuilder = jidBuilder;
		this.type = ProtocolType.parse(type);
		this.action = XMucAdminAction.parse(action);
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup() && protocol.parent().type(this.type) && protocol.cast(XMucAdmin.class).item(this.action);
	}
}
