package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavailable;

/**
 * IQ Response, type = error
 * 
 * @author kim 2014年1月20日
 */
public class IQResponseErrorProcessor extends IQResponseProcessor {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(ServiceUnavailable.DETAIL);

	public IQResponseErrorProcessor() {
		super(ProtocolType.ERROR.toString(), true);
	}

	public IQResponseErrorProcessor(boolean clear) {
		super(ProtocolType.ERROR.toString(), true, false);
	}

	public IQResponseErrorProcessor(boolean clear, boolean next) {
		super(ProtocolType.ERROR.toString(), clear, next);
	}

	protected Protocol prepare(Protocol response) {
		return response.parent().setError(this.error);
	}
}
