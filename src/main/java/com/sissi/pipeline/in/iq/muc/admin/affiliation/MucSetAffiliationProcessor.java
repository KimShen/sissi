package com.sissi.pipeline.in.iq.muc.admin.affiliation;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Item;
import com.sissi.protocol.muc.XMucAdmin;

/**
 * 更新岗位
 * 
 * @author kim 2014年3月14日
 */
public class MucSetAffiliationProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		for (Item item : protocol.cast(XMucAdmin.class).getItem()) {
			super.update(super.build(item.getJid()), group, item.getAffiliation());
		}
		return true;
	}
}
