package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * 不含资源的MUC房间
 * 
 * @author kim 2014年3月11日
 */
public class RegisterMucFieldsMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public RegisterMucFieldsMatcher(JIDBuilder jidBuilder) {
		super(Register.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean support(JID jid) {
		return jid.isGroup() && jid.isBare();
	}
}
