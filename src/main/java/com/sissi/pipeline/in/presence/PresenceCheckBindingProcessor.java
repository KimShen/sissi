package com.sissi.pipeline.in.presence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckBindingProcessor implements Input {

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return !context.isBinding() ? true : this.logAndReturn(context, protocol);
	}

	private Boolean logAndReturn(JIDContext context, Protocol protocol) {
		this.log.error("User " + context.getJid().asString() + "is dangerous on presence");
		return false;
	}
}
