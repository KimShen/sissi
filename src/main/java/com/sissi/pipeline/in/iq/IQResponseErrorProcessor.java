package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;

/**
 * @author kim 2014年1月20日
 */
public class IQResponseErrorProcessor extends IQResponseProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(ServiceUnavailable.DETAIL);

	public IQResponseErrorProcessor() {
		super(ProtocolType.ERROR.toString(), true);
	}

	public IQResponseErrorProcessor(boolean clear) {
		super(ProtocolType.ERROR.toString(), true, false);
	}

	public IQResponseErrorProcessor(boolean clear, boolean doNext) {
		super(ProtocolType.ERROR.toString(), clear, doNext);
	}

	protected Protocol prepare(Protocol response) {
		return response.parent().setError(this.error);
	}
}
