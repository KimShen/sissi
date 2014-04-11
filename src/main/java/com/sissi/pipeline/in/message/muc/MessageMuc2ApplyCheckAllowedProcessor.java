package com.sissi.pipeline.in.message.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.muc.MucApplyContext;

/**
 * @author kim 2014年3月8日
 */
public class MessageMuc2ApplyCheckAllowedProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Message message = protocol.cast(Message.class);
		return Boolean.valueOf(message.getData().findField(MucApplyContext.MUC_REQUEST_ALLOW, XField.class).getValue().toString() != "0");
	}
}
