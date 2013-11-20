package com.sissi.pipeline.process.message.p2p;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-10-24
 */
public class MessageP2PNoneRelationProcessor implements ProcessPipeline {

	private Log log = LogFactory.getLog(this.getClass());

	private JIDBuilder jidBuilder;

	private RelationContext relationContext;

	public MessageP2PNoneRelationProcessor(JIDBuilder jidBuilder, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		if (!this.relationContext.weAreFriends(this.jidBuilder.build(protocol.getTo()), context.jid())) {
			this.log.warn("We are not friends: " + context.jid().asStringWithBare() + " --> " + protocol.getTo());
			return false;
		}
		return true;
	}
}
