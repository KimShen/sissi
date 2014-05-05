package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * XMucAdmin有效性校验(都包含JID)
 * 
 * @author kim 2014年3月8日
 */
public class MucCheckAffiliationJIDProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(XMucAdmin.class).jids() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
