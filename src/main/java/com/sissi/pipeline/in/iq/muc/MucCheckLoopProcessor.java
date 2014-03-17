package com.sissi.pipeline.in.iq.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月14日
 */
public class MucCheckLoopProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(Conflict.DETAIL_ELEMENT);

	private final RelationMucMapping mapping;

	public MucCheckLoopProcessor(RelationMucMapping mapping) {
		super();
		this.mapping = mapping;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mapping.mapping(super.build(protocol.parent().getTo()).resource(protocol.cast(XMucAdmin.class).getItem().getNick())).same(context.jid()) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
