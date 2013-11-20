package com.sissi.pipeline.process.presence.subscribed;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;
import com.sissi.relation.Relation.State;

/**
 * @author kim 2013-11-5
 */
public class PresenceSubscribedMatcher implements ProcessPipelineMatcher {

	@Override
	public Boolean match(Protocol protocol) {
		return protocol.getType().equals(State.SUBSCRIBED.toString());
	}
}
