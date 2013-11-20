package com.sissi.pipeline.process.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;
import com.sissi.protocol.presence.group.X;
import com.sissi.relation.RelationContext;
import com.sissi.relation.impl.GroupWrapRelation;

/**
 * @author kim 2013-11-4
 */
public class PresenceMUCProcessor implements ProcessPipeline {

	private JIDBuilder groupBuilder;

	private RelationContext relationContext;

	public PresenceMUCProcessor(JIDBuilder groupBuilder, RelationContext relationContext) {
		super();
		this.groupBuilder = groupBuilder;
		this.relationContext = relationContext;
	}

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		JID group = this.groupBuilder.build(protocol.getTo());
		this.relationContext.subscribe(context.jid(), new GroupWrapRelation(group));
		Presence presence = Presence.class.cast(protocol);
		for (String each : this.relationContext.whoSubscribedMe(group)) {
			presence.clear();
			presence.setFrom(group.asString());
			JID memeber = this.groupBuilder.build(context.jid().asString());
			memeber.resource(context.jid().resource());
			presence.setTo(memeber.asString());
			if (context.jid().asStringWithBare().equals(each)) {
				presence.setX(new X("member", "participant", 110, 210));
			}
			context.write(presence);
		}
		return false;
	}
}
