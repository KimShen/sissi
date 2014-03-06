package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucLeave2SelfProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply());
		return true;
	}
}
