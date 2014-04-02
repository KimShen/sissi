package com.sissi.pipeline.in.iq.vcard.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.iq.vcard.VCardGetProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.muc.RelationMucMapping;
import com.sissi.ucenter.user.VCardContext;

/**
 * @author kim 2013年12月10日
 */
public class VCardGet4FansProcessor extends VCardGetProcessor {

	private final RelationMucMapping mapping;

	public VCardGet4FansProcessor(RelationMucMapping mapping, VCardContext vcardContext) {
		super(vcardContext);
		this.mapping = mapping;
	}

	@Override
	protected VCard get(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		return super.vcardContext.get(group.resource(this.mapping.mapping(group).jid().asStringWithBare()), protocol.cast(VCard.class));
	}
}
