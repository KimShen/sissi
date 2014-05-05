package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-14
 */
public class ChainedProcessor implements Input {

	private final boolean next;

	private final List<Input> processors;

	public ChainedProcessor(List<Input> processors) {
		this(false, processors);
	}

	/**
	 * @param next 执行完毕后如果input返回true是否继续执行Pipeline
	 * @param processors
	 */
	public ChainedProcessor(boolean next, List<Input> processors) {
		super();
		this.next = next;
		this.processors = processors;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		for (Input each : this.processors) {
			if (!each.input(context, protocol)) {
				return false;
			}
		}
		return this.next;
	}
}
