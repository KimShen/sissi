package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013-11-4
 */
public class RegisterMucStoreMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public RegisterMucStoreMatcher(JIDBuilder jidBuilder) {
		super(Register.class);
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Register.class).form(true) && this.jidBuilder.build(protocol.parent().getTo()).isGroup();
	}
}
