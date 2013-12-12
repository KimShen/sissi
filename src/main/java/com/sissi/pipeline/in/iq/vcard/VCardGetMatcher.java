package com.sissi.pipeline.in.iq.vcard;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * @author kim 2013年12月3日
 */
public class VCardGetMatcher extends ClassMatcher {

	private final Boolean self;

	public VCardGetMatcher(Boolean self) {
		super(VCard.class);
		this.self = self;
	}

	@Override
	public Boolean match(Protocol protocol) {
		return super.match(protocol) && (IQ.class.cast(protocol.getParent()).getTo() == null) == this.self;
	}
}