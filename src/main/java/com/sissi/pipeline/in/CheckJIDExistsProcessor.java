package com.sissi.pipeline.in;

import java.util.Set;

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

	private final Set<String> ignore;

	public CheckJIDExistsProcessor(VCardContext vcardContext, Set<String> ignore) {
		this(vcardContext, true, ignore);
	}

	public CheckJIDExistsProcessor(VCardContext vcardContext, boolean presenceIgnore, Set<String> ignore) {
		super();
		this.ignore = ignore;
		this.vcardContext = vcardContext;
		this.presenceIgnore = presenceIgnore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		// Not contain "to" or "to" in special addresses
		// Not presence node
		// JID exists
		return !protocol.to() || protocol.to(this.ignore) || this.presenceIgnore && protocol.clazz(Presence.class) || this.vcardContext.exists(protocol.getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(this.error));
		return false;
	}
}
