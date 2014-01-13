package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月24日
 */
public class Bytestreams2ActivateProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().clear().reply().setTo(context.getJid()).setType(Type.RESULT));
		return true;
	}
}
