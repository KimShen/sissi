package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckChildrenProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return ProtocolType.parse(protocol.getType()).in(ProtocolType.SET, ProtocolType.GET) ? (IQ.class.cast(protocol).listChildren().isEmpty() ? this.writeAndReturn(context, protocol) : true) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.reply().setError(this.error));
		return false;
	}
}
