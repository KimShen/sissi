package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.DiscoInfo;
import com.sissi.protocol.iq.disco.Identity;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年4月8日
 */
public class DiscoInfoMucReserveProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public DiscoInfoMucReserveProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		String reserve = this.mucConfigBuilder.build(super.build(protocol.parent().getTo())).reserve(context.jid());
		context.write(protocol.cast(DiscoInfo.class).add(reserve != null ? new Identity(reserve, "text", "conference") : null).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
