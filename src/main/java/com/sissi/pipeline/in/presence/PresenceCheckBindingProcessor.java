package com.sissi.pipeline.in.presence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.NotAuthorized;

/**
 * @author kim 2014年1月14日
 */
public class PresenceCheckBindingProcessor implements Input {

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.isBinding() ? true : this.logAndReturn(context, protocol);
	}

	private Boolean logAndReturn(JIDContext context, Protocol protocol) {
		this.log.error("User " + context.getJid().asString() + "is dangerous on presence");
		return !context.write(Stream.closeSuddenly(new ServerError().add(NotAuthorized.DETAIL)).setFrom(context.getDomain()).setTo(protocol.getFrom())).close();
	}
}
