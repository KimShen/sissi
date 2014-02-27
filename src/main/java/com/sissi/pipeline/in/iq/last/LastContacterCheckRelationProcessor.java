package com.sissi.pipeline.in.iq.last;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.iq.last.Last;

/**
 * @author kim 2014年1月26日
 */
public class LastContacterCheckRelationProcessor extends CheckRelationProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Last.class).seconds().parent().reply().setError(this.error));
		return false;
	}
}
