package com.sissi.pipeline.in.message.apply;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.relation.muc.apply.RequestConfig;

/**
 * 表单MUC#jid存在性校验
 * 
 * @author kim 2014年3月8日
 */
public class MessageApplyCheckJIDProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Message.class).data(RequestConfig.JID.toString()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
