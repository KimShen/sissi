package com.sissi.pipeline.in.iq.roster;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2014年1月14日
 */
public class RosterSetCheckExistsProcessor extends ProxyProcessor {

	private final VCardContext vcardContext;

	public RosterSetCheckExistsProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.vcardContext.exists(super.build(Roster.class.cast(protocol).getFirstItem().getJid())) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setFrom(context.getDomain()).setError(new ServerError().setType(ProtocolType.CANCEL).add(ItemNotFound.DETAIL)));
		return false;
	}
}
