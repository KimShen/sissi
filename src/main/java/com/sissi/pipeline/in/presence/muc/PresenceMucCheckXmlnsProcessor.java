package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucCheckXmlnsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.MODIFY).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Presence.class).findFields(XMuc.NAME).isEmpty() ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}

}
