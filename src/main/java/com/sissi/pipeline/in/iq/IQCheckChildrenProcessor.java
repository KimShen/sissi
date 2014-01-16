package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckChildrenProcessor implements Input {

	private final String ERROR_TEXT = "SET/GET must one sub element";

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		ProtocolType type = ProtocolType.parse(protocol.getType());
		return (type == ProtocolType.SET || type == ProtocolType.GET) ? (IQ.class.cast(protocol).listChildren().isEmpty() ? this.writeAndReturn(context, protocol) : true) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.reply().setError(new ServerError().setType(ProtocolType.CANCEL).setBy(context.getDomain()).add(BadRequest.DETAIL, context.getLang(), ERROR_TEXT)));
		return false;
	}
}
