package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.MucMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.register.Register;

/**
 * jabber:x:data
 * 
 * @author kim 2013-11-4
 */
public class RegisterMucStoreMatcher extends MucMatcher {

	public RegisterMucStoreMatcher(JIDBuilder jidBuilder) {
		super(Register.class, jidBuilder);
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(Register.class).form(true);
	}
}
