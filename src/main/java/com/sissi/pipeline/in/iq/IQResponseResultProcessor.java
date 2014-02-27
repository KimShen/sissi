package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月20日
 */
public class IQResponseResultProcessor extends IQResponseProcessor {

	public IQResponseResultProcessor() {
		super(ProtocolType.RESULT.toString(), true);
	}

	public IQResponseResultProcessor(boolean clear) {
		super(ProtocolType.RESULT.toString(), true, false);
	}

	public IQResponseResultProcessor(boolean clear, boolean doNext) {
		super(ProtocolType.RESULT.toString(), clear, doNext);
	}

	protected Protocol prepare(Protocol response) {
		return response;
	}
}
