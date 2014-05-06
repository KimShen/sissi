package com.sissi.pipeline.in.iq.vcard;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 指定JID VCard
 * @author kim 2013年12月10日
 */
public class VCardGet4FansProcessor extends VCardGetProcessor {

	public VCardGet4FansProcessor(VCardContext vcardContext) {
		super(vcardContext);
	}

	@Override
	protected VCard get(JIDContext context, Protocol protocol) {
		return super.vcardContext.pull(super.build(protocol.parent().getTo()), protocol.cast(VCard.class));
	}
}
