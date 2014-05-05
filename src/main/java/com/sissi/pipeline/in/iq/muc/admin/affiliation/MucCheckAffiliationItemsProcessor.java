package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;
import com.sissi.ucenter.relation.muc.validate.ItemValidator;

/**
 * 岗位Item集合校验
 * 
 * @author kim 2014年3月14日
 */
public class MucCheckAffiliationItemsProcessor extends ProxyProcessor {

	private final ItemValidator validator;

	public MucCheckAffiliationItemsProcessor(ItemValidator validator) {
		super();
		this.validator = validator;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			ItemStatus error = this.validator.valdate(context, group, super.build(item.getJid()));
			if (!error.valid()) {
				return this.writeAndReturn(context, protocol, error.error());
			}
		}
		return true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol, Error error) {
		context.write(protocol.parent().reply().setError(error));
		return false;
	}
}
