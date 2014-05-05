package com.sissi.pipeline.in.iq.vcard;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;

/**
 * <iq from='stpeter@jabber.org/roundabout' id='v3' to='jer@jabber.org' type='get'><vCard xmlns='vcard-temp'/></iq>
 * 
 * @author kim 2013年12月3日
 */
public class VCardGetMatcher extends ClassMatcher {

	private final boolean to;

	/**
	 * @param to 是否包含To
	 */
	public VCardGetMatcher(boolean to) {
		super(VCard.class);
		this.to = to;
	}

	@Override
	public boolean match(Protocol protocol) {
		return super.match(protocol) && !protocol.parent().to() == this.to;
	}
}