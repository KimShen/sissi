package com.sissi.pipeline.in.message.apply;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelationContext;
import com.sissi.ucenter.relation.muc.apply.ApplyContext;

/**
 * 表单处理
 * 
 * @author kim 2014年3月8日
 */
public class MessageApplyProcessor extends ProxyProcessor {

	private final String subject;

	private final ApplyContext requestContext;

	private final MucRelationContext relationContext;

	public MessageApplyProcessor(String subject, ApplyContext requestContext, MucRelationContext relationContext) {
		super();
		this.subject = subject;
		this.requestContext = requestContext;
		this.relationContext = relationContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		JID group = super.build(protocol.parent().getTo());
		if (this.requestContext.apply(context.jid(), group, protocol.cast(Message.class).noneThread().subject(this.subject).getData())) {
			for (Relation relation : this.relationContext.myRelations(group, ItemRole.MODERATOR.toString())) {
				super.findOne(super.build(relation.jid()), true).write(protocol.reply());
			}
		}
		return true;
	}
}
