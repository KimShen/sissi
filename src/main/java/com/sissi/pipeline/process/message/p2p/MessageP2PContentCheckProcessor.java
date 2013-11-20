package com.sissi.pipeline.process.message.p2p;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;

/**
 * @author kim 2013-10-24
 */
public class MessageP2PContentCheckProcessor implements ProcessPipeline {

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		return Message.class.cast(protocol).hasContent() ? true : false;
	}
}
