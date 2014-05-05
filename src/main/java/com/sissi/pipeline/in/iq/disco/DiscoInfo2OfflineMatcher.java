package com.sissi.pipeline.in.iq.disco;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;
import com.sissi.pipeline.in.ClassMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.DiscoInfo;

/**
 * 匹配离线用户
 * 
 * @author kim 2014年4月30日
 */
public class DiscoInfo2OfflineMatcher extends ClassMatcher {

	private final JIDBuilder jidBuilder;

	private final Addressing addressing;

	public DiscoInfo2OfflineMatcher(JIDBuilder jidBuilder, Addressing addressing) {
		super(DiscoInfo.class);
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
	}

	public boolean match(Protocol protocol) {
		return super.match(protocol) && this.addressing.resources(this.jidBuilder.build(protocol.parent().getTo()), true).isEmpty();
	}
}
