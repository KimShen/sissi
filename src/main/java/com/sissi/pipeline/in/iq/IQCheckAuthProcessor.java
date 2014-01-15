package com.sissi.pipeline.in.iq;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.ServerErrorText.Xmlns;
import com.sissi.protocol.error.element.BadRequest;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckAuthProcessor implements Input {

	private final String ERROR_TEXT = "Please auth first";

	private final Set<Class<? extends Protocol>> authes;

	public IQCheckAuthProcessor(Set<Class<? extends Protocol>> shouldAuth) {
		super();
		this.authes = shouldAuth;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isAuth() || !this.authes.contains(protocol.getClass()) ? true : this.writeAndReturn(context, protocol);
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.reply().setError(new ServerError().setType(Type.AUTH).add(BadRequest.DETAIL, context.getLang(), ERROR_TEXT, Xmlns.XMLNS_STANZAS)));
		return false;
	}
}
