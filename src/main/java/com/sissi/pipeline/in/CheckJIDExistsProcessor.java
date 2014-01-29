package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
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

	private final VCardContext vcardContext;

	private final Boolean presenceIgnore;

	private final String domain;

	private final String proxy;

	public CheckJIDExistsProcessor(VCardContext vcardContext, String domain, String proxy) {
		super();
		this.vcardContext = vcardContext;
		this.domain = domain;
		this.proxy = proxy;
		this.presenceIgnore = true;
	}
	
	public CheckJIDExistsProcessor(VCardContext vcardContext, Boolean presenceIgnore, String domain, String proxy) {
		super();
		this.vcardContext = vcardContext;
		this.presenceIgnore = presenceIgnore;
		this.domain = domain;
		this.proxy = proxy;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return protocol.getTo() == null || this.presenceIgnore && (protocol.getClass() == Presence.class) || this.vcardContext.exists(super.build(protocol.getTo())) || this.domain.equals(protocol.getTo()) || this.proxy.equals(protocol.getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().clear().reply().setError(new ServerError().setType(ProtocolType.CANCEL).add(ServiceUnavaliable.DETAIL)));
		return false;
	}
}
