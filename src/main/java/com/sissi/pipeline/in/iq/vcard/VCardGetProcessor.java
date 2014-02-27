package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.VCardContext;

/**
 * @author kim 2013年12月10日
 */
abstract class VCardGetProcessor extends ProxyProcessor {

	protected final VCardContext vcardContext;

	public VCardGetProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(this.get(context, protocol).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}

	abstract protected VCard get(JIDContext context, Protocol protocol);
}
