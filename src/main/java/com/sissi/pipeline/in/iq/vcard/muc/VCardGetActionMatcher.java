package com.sissi.pipeline.in.iq.vcard.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2013年12月3日
 */
public class VCardGetActionMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final boolean bare;

	public VCardGetActionMatcher(JIDBuilder jidBuilder, boolean bare) {
		super(VCard.class);
		this.jidBuilder = jidBuilder;
		this.bare = bare;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean support(JID jid) {
		return jid.isGroup() && jid.isBare() == this.bare;
	}
}