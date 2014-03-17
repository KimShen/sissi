package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * @author kim 2014年3月14日
 */
public class MucGetMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public MucGetMatcher(JIDBuilder jidBuilder) {
		super(XMucAdmin.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup() && protocol.parent().type(ProtocolType.GET) && protocol.cast(XMucAdmin.class).item(1);
	}
}
