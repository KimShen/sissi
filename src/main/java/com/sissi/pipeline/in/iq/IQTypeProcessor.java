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

	private Boolean clear;

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
		context.write(this.prepareIQ(protocol));
		return false;
	}

	private Protocol prepareIQ(Protocol protocol) {
		return this.clear ? protocol.getParent().reply().clear().setType(this.type) : protocol.getParent().reply().setType(this.type);
	}
}
