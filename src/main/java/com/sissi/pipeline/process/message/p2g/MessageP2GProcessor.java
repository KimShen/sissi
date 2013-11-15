package com.sissi.pipeline.process.message.p2g;

import com.sissi.addressing.Addressing;
import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.relation.RelationContext;

/**
 * @author kim 2013-10-24
 */
public class MessageP2GProcessor implements ProcessPipeline {

	private JIDBuilder jidBuilder;

	private Addressing addressing;

	private RelationContext relationContext;

	public MessageP2GProcessor(JIDBuilder jidBuilder, Addressing addressing, RelationContext relationContext) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		Message message = Message.class.cast(protocol);
		JID group = this.jidBuilder.build(protocol.getTo());
		group.resource(context.jid().asStringWithBare());
		message.setFrom(group.asString());
		if (message.hasContent()) {
			for (String relation : this.relationContext.whoSubscribedMe(this.jidBuilder.build(protocol.getTo()))) {
				JIDContext toContext = this.addressing.find(this.jidBuilder.build(relation));
				if (toContext != null) {
					message.setTo(relation);
					toContext.write(message);
				}
			}
		}
		return false;
	}
}
