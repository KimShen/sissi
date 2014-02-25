package com.sissi.pipeline.in.iq;

import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ServiceUnavaliable;

/**
 * @author kim 2014年1月20日
 */
public class IQErrorProcessor extends IQProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(ServiceUnavaliable.DETAIL);

	public IQErrorProcessor() {
		super(ProtocolType.ERROR.toString(), true);
	}

	public IQErrorProcessor(boolean clear) {
		super(ProtocolType.ERROR.toString(), true, false);
	}

	public IQErrorProcessor(boolean clear, boolean doNext) {
		super(ProtocolType.ERROR.toString(), clear, doNext);
	}

	protected Protocol prepare(Protocol response) {
		return response.setError(this.error);
	}
}
