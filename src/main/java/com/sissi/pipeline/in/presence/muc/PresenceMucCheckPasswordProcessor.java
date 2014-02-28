package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAuthorized;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.muc.MucGroupConfig;
import com.sissi.ucenter.muc.MucGroupContext;

/**
 * @author kim 2014年2月22日
 */
public class PresenceMucCheckPasswordProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(NotAuthorized.DETAIL_ELEMENT);

	private final MucGroupContext mucGroupContext;

	public PresenceMucCheckPasswordProcessor(MucGroupContext mucGroupContext) {
		super();
		this.mucGroupContext = mucGroupContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMuc xmuc = Presence.class.cast(protocol).findField(XMuc.NAME, XMuc.class);
		return this.mucGroupContext.find(super.build(protocol.getTo())).allowed(MucGroupConfig.PASSWORD, xmuc != null ? xmuc.getPassword() : null) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
