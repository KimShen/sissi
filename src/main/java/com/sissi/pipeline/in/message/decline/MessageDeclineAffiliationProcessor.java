package com.sissi.pipeline.in.message.decline;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

/**
 * 删除岗位
 * 
 * @author kim 2014年3月10日
 */
public class MessageDeclineAffiliationProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.remove(context.jid(), super.build(protocol.parent().getTo()));
		return true;
	}
}