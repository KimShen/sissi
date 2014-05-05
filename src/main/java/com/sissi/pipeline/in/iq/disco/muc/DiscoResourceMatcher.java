package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.Disco;

/**
 * <iq to='MUC JID' type='get'><query xmlns='http://jabber.org/protocol/disco#info(disco#items)'/></iq>
 * 
 * @author kim 2014年3月12日
 */
public class DiscoResourceMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final boolean resource;

	/**
	 * @param clazz
	 * @param jidBuilder
	 * @param resource 是否包含资源
	 */
	public DiscoResourceMatcher(Class<? extends Disco> clazz, JIDBuilder jidBuilder, boolean resource) {
		super(clazz);
		this.jidBuilder = jidBuilder;
		this.resource = resource;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.support(this.jidBuilder.build(protocol.parent().getTo()));
	}

	private boolean support(JID jid) {
		return jid.isGroup() && jid.isBare() == this.resource;
	}
}