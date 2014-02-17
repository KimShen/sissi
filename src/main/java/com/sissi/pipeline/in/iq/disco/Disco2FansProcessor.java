package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月19日
 */
public class Disco2FansProcessor extends ProxyProcessor {

	private final DiscoFeature[] features;

	private final boolean forceProxy;

	public Disco2FansProcessor() {
		super();
		this.features = new DiscoFeature[] {};
		this.forceProxy = false;
	}

	public Disco2FansProcessor(DiscoFeature[] features) {
		this.features = features;
		this.forceProxy = true;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (this.forceProxy) {
			Disco.class.cast(protocol).add(this.features);
		}
		super.findOne(super.build(protocol.getParent().getTo()), true).write(protocol.getParent().setFrom(context.jid()));
		return true;
	}
}
