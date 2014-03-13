package com.sissi.pipeline.in.presence.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.message.MessageType;
import com.sissi.protocol.message.Subject;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucJoin2SelfMessageSubjectProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public PresenceMucJoin2SelfMessageSubjectProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.getTo());
		String subject = mucConfigBuilder.build(group).pull(MongoConfig.FIELD_SUBJECT).toString();
		if (subject != null) {
			context.write(new Message().noneThread().setSubject(new Subject(subject)).setType(MessageType.GROUPCHAT).setFrom(group.resource(super.ourRelation(context.jid(), group).name())));
		}
		return true;
	}
}
