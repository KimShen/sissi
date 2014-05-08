package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * 如果不存在则使用JIDContext.jid填充
 * 
 * @author kim 2014年5月8日
 */
public class CheckJIDFromProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		protocol.parent().setFrom(context.jid());
		return true;
	}
}
