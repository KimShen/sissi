package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2013年12月3日
 */
abstract public class IQResponseProcessor implements Input {

	private final ProtocolType type;

	private final boolean next;

	private final boolean clear;

	public IQResponseProcessor(String type) {
		this(type, true);
	}

	public IQResponseProcessor(String type, boolean clear) {
		this(type, true, false);
	}

	/**
	 * @param type
	 * @param clear Protocol.clear
	 * @param next 如果Input返回true是否继续执行Pipeline
	 */
	public IQResponseProcessor(String type, boolean clear, boolean next) {
		this.type = ProtocolType.parse(type);
		this.clear = clear;
		this.next = next;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Protocol response = this.prepare(protocol.parent().reply().setType(this.type));
		context.write(this.clear ? response.clear() : response);
		return this.next;
	}

	abstract protected Protocol prepare(Protocol response);
}
