package com.sissi.pipeline.in.iq.muc.admin;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * XMucAdmin有效性校验(禁止角色/岗位同时出现)
 * 
 * @author kim 2014年3月29日
 */
public class MucCheckConflictProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(XMucAdmin.class).vaild() ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
