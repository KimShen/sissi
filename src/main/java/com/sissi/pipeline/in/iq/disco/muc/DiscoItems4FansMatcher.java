package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.DiscoItems;

/**
 * <query xmlns='http://jabber.org/protocol/disco#items' node='http://jabber.org/protocol/muc#rooms'/>
 * 
 * @author kim 2014年4月3日
 */
public class DiscoItems4FansMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	public DiscoItems4FansMatcher(JIDBuilder jidBuilder) {
		super(DiscoItems.class);
		this.jidBuilder = jidBuilder;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && protocol.cast(DiscoItems.class).node() && this.support(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean support(JID jid) {
		return !jid.isGroup() && !jid.isBare();
	}
}
