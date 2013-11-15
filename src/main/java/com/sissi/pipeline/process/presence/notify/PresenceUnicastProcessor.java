package com.sissi.pipeline.process.presence.notify;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2013-11-14
 */
public abstract class PresenceUnicastProcessor implements ProcessPipeline {

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		context.write(this.prepare(context, Presence.class.cast(protocol)));
		return true;
	}

	abstract public Presence prepare(JIDContext context, Presence presence);
}