package com.sissi.pipeline.in.iq;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.ServerErrorText.Xmlns;
import com.sissi.protocol.error.element.BadRequest;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckTypeContentProcessor implements Input {

	private final String ERROR_TEXT = "SET/GET must one sub element";

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		Type type = Type.parse(protocol.getType());
		return type == Type.SET || type == Type.GET ? this.writeAndReturn(context, IQ.class.cast(protocol)) : true;
	}

	private Boolean writeAndReturn(JIDContext context, IQ protocol) {
		if (protocol.listChildren().isEmpty()) {
			context.write(protocol.reply().setError(new ServerError().setBy(context.getDomain()).add(BadRequest.DETAIL, context.getLang(), ERROR_TEXT, Xmlns.XMLNS_STANZAS)));
			return false;
		} else {
			return true;
		}
	}
}
