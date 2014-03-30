package com.sissi.pipeline.in.iq.vcard.muc;

import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2013年12月3日
 */
public class VCardGetMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public VCardGetMatcher(JIDBuilder jidBuilder) {
		super(VCard.class);
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.jidBuilder.build(protocol.parent().getTo()).isGroup();
	}
}