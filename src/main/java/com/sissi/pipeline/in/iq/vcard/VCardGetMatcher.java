package com.sissi.pipeline.in.iq.vcard;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2013年12月3日
 */
public class VCardGetMatcher extends ClassMatcher {

	private final boolean self;

	public VCardGetMatcher(boolean self) {
		super(VCard.class);
		this.self = self;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && !protocol.parent().to() == this.self;
	}
}