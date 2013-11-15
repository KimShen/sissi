package com.sissi.pipeline.process.presence.state;

import com.sissi.pipeline.ProcessPipelineMatcher;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-5
 */
public class PresenceFriendsMatcher implements ProcessPipelineMatcher {

	@Override
	public Boolean match(Protocol protocol) {
		return protocol.getType() == null;
	}
}
