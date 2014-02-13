package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.DiscoFeature;
import com.sissi.protocol.iq.disco.DiscoInfo;

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
		context.write(DiscoInfo.class.cast(protocol).add(this.features).getParent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
