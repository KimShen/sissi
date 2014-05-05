package com.sissi.pipeline.in;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * JID真实性校验(不作用于MUC JID)
 * 
 * @author kim 2014年1月24日
 */
public class CheckJIDExistsProcessor extends ProxyProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(ServiceUnavailable.DETAIL, "JID not exists");

	private final VCardContext vcardContext;

	private final boolean presenceIgnore;

	private final Set<String> domains;

	/**
	 * @param domains 需要忽略的域
	 * @param presenceIgnore
	 * @param vcardContext
	 */
	public CheckJIDExistsProcessor(Set<String> domains, boolean presenceIgnore, VCardContext vcardContext) {
		super();
		this.domains = domains;
		this.vcardContext = vcardContext;
		this.presenceIgnore = presenceIgnore;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || protocol.to(this.domains) || this.presenceIgnore && protocol.clazz(Presence.class) || super.build(protocol.getTo()).isGroup() || this.vcardContext.exists(protocol.getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
