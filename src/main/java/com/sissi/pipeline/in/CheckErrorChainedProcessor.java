package com.sissi.pipeline.in;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * Error责任链
 * 
 * @author kim 2014年5月8日
 */
public class CheckErrorChainedProcessor extends ChainedProcessor {

	public CheckErrorChainedProcessor(List<Input> processors) {
		super(processors);
	}

	/**
	 * @param next Error是否继续执行
	 * @param processors
	 */
	public CheckErrorChainedProcessor(boolean next, List<Input> processors) {
		super(next, processors);
	}

	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.type(ProtocolType.ERROR) ? super.input(context, protocol) : true;
	}
}
