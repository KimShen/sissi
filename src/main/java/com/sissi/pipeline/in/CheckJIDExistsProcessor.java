package com.sissi.pipeline.in;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2014年1月24日
 */
public class CheckJIDExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(ServiceUnavailable.DETAIL);

	private final VCardContext vcardContext;

	private final boolean presenceIgnore;

	private final Set<String> domains;

	public CheckJIDExistsProcessor(boolean presenceIgnore, Set<String> domains, VCardContext vcardContext) {
		super();
		this.domains = domains;
		this.vcardContext = vcardContext;
		this.presenceIgnore = presenceIgnore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || protocol.to(this.domains) || this.presenceIgnore && protocol.clazz(Presence.class) || this.vcardContext.exists(protocol.getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
