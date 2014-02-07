package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavaliable;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2014年1月24日
 */
public class CheckJIDExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(ServiceUnavaliable.DETAIL);

	private final VCardContext vcardContext;

	private final boolean presenceIgnore;

	private final String domain;

	private final String proxy;

	public CheckJIDExistsProcessor(VCardContext vcardContext, String domain, String proxy) {
		this(vcardContext, true, domain, proxy);
	}

	public CheckJIDExistsProcessor(VCardContext vcardContext, Boolean presenceIgnore, String domain, String proxy) {
		super();
		this.proxy = proxy;
		this.domain = domain;
		this.vcardContext = vcardContext;
		this.presenceIgnore = presenceIgnore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || protocol.to(this.domain) || protocol.to(this.proxy) || this.presenceIgnore && protocol.clazz(Presence.class) || this.vcardContext.exists(protocol.getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().clear().reply().setError(this.error));
		return false;
	}
}
