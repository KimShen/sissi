package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;

/**
 * @author kim 2014年2月24日
 */
public class Bytestreams2FansCheckRelationProcessor extends CheckRelationProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	public Bytestreams2FansCheckRelationProcessor(boolean free) {
		super(free);
	}

	@Override
	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
