package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月3日
 */
public class IQTypeProcessor implements Input {

	private Type type;

	public IQTypeProcessor(String type) {
		super();
		this.type = Type.parse(type);
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.prepareIQ(protocol));
		return false;
	}

	private Protocol prepareIQ(Protocol protocol) {
		return protocol.getParent().reply().clear().setType(type);
	}
}
