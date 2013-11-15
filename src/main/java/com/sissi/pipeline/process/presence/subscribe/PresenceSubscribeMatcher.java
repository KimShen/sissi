package com.sissi.pipeline.process.presence.subscribe;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.relation.Relation.State;

/**
 * @author kim 2013-11-5
 */
public class PresenceSubscribeMatcher implements ProcessPipelineMatcher {

	@Override
	public Boolean match(Protocol protocol) {
		return protocol.getType().equals(State.SUBSCRIBE.toString());
	}
}
