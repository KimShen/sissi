package com.sissi.pipeline.in.iq;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;

/**
 * @author kim 2014年1月14日
 */
public class IQCheckAuthProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(BadRequest.DETAIL);

	private final Set<Class<? extends Protocol>> authes;

	public IQCheckAuthProcessor(Set<Class<? extends Protocol>> authes) {
		super();
		this.authes = authes;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return (context.auth() || !this.authes.contains(protocol.getClass())) ? true : !context.write(protocol.reply().setError(this.error)).close();
	}
}
