package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;

/**
 * IQ Response, type = result
 * 
 * @author kim 2014年1月20日
 */
public class IQResponseResultProcessor extends IQResponseProcessor {

	public IQResponseResultProcessor() {
		this(true);
	}

	public IQResponseResultProcessor(boolean clear) {
		this(clear, false);
	}

	public IQResponseResultProcessor(boolean clear, boolean doNext) {
		super(ProtocolType.RESULT.toString(), clear, doNext);
	}

	protected Protocol prepare(Protocol response) {
		return response;
	}
}
