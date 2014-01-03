package com.sissi.pipeline.in.security;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.ElementlError;
import com.sissi.protocol.error.detail.NotAuthorized;

/**
 * @author kim 2014年1月2日
 */
public class BindedProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : this.notAuthorized(context, protocol);
	}

	private Boolean notAuthorized(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().reply().setError(new ElementlError().add(NotAuthorized.DETAIL).setType(Type.CANCEL)));
		return false;
	}

}
