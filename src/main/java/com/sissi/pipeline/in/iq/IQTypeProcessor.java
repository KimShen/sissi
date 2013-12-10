package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月3日
 */
public class IQTypeProcessor implements Input {

	private final Type type;

	private final Boolean clear;

	public IQTypeProcessor(String type) {
		this(type, true);
	}

	public IQTypeProcessor(String type, Boolean clear) {
		super();
		this.type = Type.parse(type);
		this.clear = clear;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.prepare(protocol));
		return false;
	}

	private Protocol prepare(Protocol protocol) {
		Protocol response = protocol.getParent().reply().setType(this.type);
		return this.clear ? response.clear() : response;
	}
}
