package com.sissi.pipeline.in.iq;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckAuthProcessor implements Input {

	private final String text = "Please auth first";

	private final Set<Class<? extends Protocol>> authes;

	public IQCheckAuthProcessor(Set<Class<? extends Protocol>> authes) {
		super();
		this.authes = authes;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return (context.isAuth() || !this.authes.contains(protocol.getClass())) ? true : !context.write(protocol.reply().setError(new ServerError().setType(ProtocolType.AUTH).add(BadRequest.DETAIL, context.getLang(), this.text))).close();
	}
}
