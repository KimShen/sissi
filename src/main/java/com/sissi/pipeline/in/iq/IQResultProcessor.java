package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * @author kim 2014年1月20日
 */
public class IQResultProcessor extends IQProcessor {

	public IQResultProcessor() {
		super(ProtocolType.RESULT.toString(), true);
	}

	public IQResultProcessor(Boolean clear) {
		super(ProtocolType.RESULT.toString(), true, false);
	}

	public IQResultProcessor(Boolean clear, Boolean doNext) {
		super(ProtocolType.RESULT.toString(), clear, doNext);
	}

	protected Protocol prepare(Protocol response) {
		return response;
	}
}
