package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.ServerErrorText.Xmlns;
import com.sissi.protocol.error.element.ServiceUnavaliable;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2014年1月15日
 */
public class MessageValidationProcessor implements Input {

	private final String ERROR = "Message must have one element";

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return Message.class.cast(protocol).isValid() ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(new ServerError().setType(Type.MODIFY).add(ServiceUnavaliable.DETAIL, context.getLang(), ERROR, Xmlns.XMLNS_STANZAS)));
		return false;
	}
}
