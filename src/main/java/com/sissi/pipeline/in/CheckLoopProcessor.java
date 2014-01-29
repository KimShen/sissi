package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月29日
 */
public class CheckLoopProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !ProtocolType.ERROR.equals(protocol.getType());
	}
}
