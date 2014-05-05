package com.sissi.pipeline.in;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * 如果Protocol.type = error则终止Pipeline
 * 
 * @author kim 2014年2月21日
 */
public class CheckErrorLoopProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) ? false : true;
	}
}
