package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.SignatureContext;

/**
 * @author kim 2014年1月27日
 */
public class PresenceLeaveSignatureProcessor extends ProxyProcessor {

	private final SignatureContext signatureContext;

	public PresenceLeaveSignatureProcessor(SignatureContext signatureContext) {
		super();
		this.signatureContext = signatureContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.signatureContext.signature(context.getJid(), Presence.class.cast(protocol).getStatusAsText());
		return true;
	}
}
