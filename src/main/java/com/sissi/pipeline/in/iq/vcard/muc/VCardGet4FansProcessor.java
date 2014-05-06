package com.sissi.pipeline.in.iq.vcard.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.iq.vcard.VCardGetProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * <iq to='MUC JID' type='get'><vCard xmlns='vcard-temp'/></iq></p>
 * 
 * @author kim 2013年12月10日
 */
public class VCardGet4FansProcessor extends VCardGetProcessor {

	private final MucRelationMapping mapping;

	public VCardGet4FansProcessor(MucRelationMapping mapping, VCardContext vcardContext) {
		super(vcardContext);
		this.mapping = mapping;
	}

	@Override
	protected VCard get(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		return super.vcardContext.pull(group.resource(this.mapping.mapping(group).jid().asStringWithBare()), protocol.cast(VCard.class));
	}
}
