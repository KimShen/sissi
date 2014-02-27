package com.sissi.pipeline.in.presence.probe;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.PresenceType;

/**
 * @author kim 2014年1月27日
 */
public class PresenceProbeCheckRelationProcessor extends CheckRelationProcessor {

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Presence.class).setType(PresenceType.UNSUBSCRIBED).reply());
		return false;
	}
}
