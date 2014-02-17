package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2013年12月3日
 */
abstract public class IQProcessor implements Input {

	private final ProtocolType type;

	private final boolean clear;

	private final boolean doNext;

	public IQProcessor(String type) {
		this(type, true);
	}

	public IQProcessor(String type, boolean clear) {
		this(type, true, false);
	}

	public IQProcessor(String type, boolean clear, Boolean doNext) {
		this.type = ProtocolType.parse(type);
		this.clear = clear;
		this.doNext = doNext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Protocol response = this.prepare(protocol.getParent().reply().setType(this.type));
		context.write(this.clear ? response.clear() : response);
		return this.doNext;
	}

	abstract protected Protocol prepare(Protocol response);
}
