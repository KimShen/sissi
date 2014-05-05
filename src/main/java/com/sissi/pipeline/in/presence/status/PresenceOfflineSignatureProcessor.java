package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.field.impl.BeanField;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * Presence unavailable, 持久化签名
 * 
 * @author kim 2014年1月27日
 */
public class PresenceOfflineSignatureProcessor extends ProxyProcessor {

	private final VCardContext vcardContext;

	public PresenceOfflineSignatureProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.vcardContext.set(context.jid(), new BeanField<String>().name(VCardContext.FIELD_SIGNATURE).value(protocol.cast(Presence.class).getStatusAsText()));
		return true;
	}
}
