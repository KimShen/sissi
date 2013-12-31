package com.sissi.pipeline.in.presence.muc;

import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.XMuc;

/**
 * @author kim 2013年12月29日
 */
public class PresenceMucMatcher extends ClassMatcher {

	public PresenceMucMatcher() {
		super(Presence.class);
	}

	public Boolean match(Protocol protocol) {
		return super.match(protocol) && Presence.class.cast(protocol).findField(XMuc.NAME, XMuc.class) != null;
	}
}
