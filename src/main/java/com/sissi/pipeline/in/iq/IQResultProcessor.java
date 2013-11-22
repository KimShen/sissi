package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013-10-24
 */
public class IQResultProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.prepareIQ(protocol));
		return false;
	}

	private Protocol prepareIQ(Protocol protocol) {
		return protocol.getParent().reply().clear().setType(Type.RESULT);
	}
}
