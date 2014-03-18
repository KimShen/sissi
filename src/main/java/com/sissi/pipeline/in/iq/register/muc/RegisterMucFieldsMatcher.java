package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2014年3月11日
 */
public class RegisterMucFieldsMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public RegisterMucFieldsMatcher(JIDBuilder jidBuilder) {
		super(Register.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.muc(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean muc(JID jid) {
		return jid.isGroup() && jid.isBare();
	}
}
