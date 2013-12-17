package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;

/**
 * @author kim 2013年12月3日
 */
public class IQProcessor implements Input {

	private final Type type;

	private final Boolean clear;

	private final Boolean doNext;

	public IQProcessor(String type) {
		this(type, true);
	}

	public IQProcessor(String type, Boolean clear) {
		this(type, true, false);
	}

	public IQProcessor(String type, Boolean clear, Boolean doNext) {
		this.type = Type.parse(type);
		this.clear = clear;
		this.doNext = doNext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(this.prepare(protocol));
		return this.doNext;
	}

	private Protocol prepare(Protocol protocol) {
		Protocol response = protocol.getParent().reply().setType(this.type);
		return this.clear ? response.close() : response;
	}
}
