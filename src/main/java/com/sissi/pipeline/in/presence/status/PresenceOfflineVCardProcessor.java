package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.VCardContext;
import com.sissi.ucenter.field.impl.BeanField;

/**
 * @author kim 2014年1月27日
 */
public class PresenceOfflineVCardProcessor extends ProxyProcessor {

	private final VCardContext vcardContext;

	public PresenceOfflineVCardProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.vcardContext.set(context.jid(), new BeanField<String>().setName(VCardContext.FIELD_SIGNATURE).setValue(Presence.class.cast(protocol).getStatusAsText()));
		return true;
	}
}
