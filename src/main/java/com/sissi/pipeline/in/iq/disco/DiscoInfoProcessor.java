package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2014年2月13日
 */
public class DiscoInfoProcessor implements Input {

	private final DiscoFeature[] features;

	public DiscoInfoProcessor(DiscoFeature[] features) {
		this.features = features;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Disco.class).add(this.features).getParent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
