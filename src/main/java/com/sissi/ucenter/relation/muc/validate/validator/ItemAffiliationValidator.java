package com.sissi.ucenter.relation.muc.validate.validator;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.NotAllowed;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.RelationContext;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.validate.ItemStatus;
import com.sissi.ucenter.relation.muc.validate.ItemValidator;
import com.sissi.ucenter.relation.muc.validate.status.ValidItemStatus;
import com.sissi.ucenter.relation.muc.validate.status.WrongItemStatus;

/**
 * Item岗位有效性校验
 * 
 * @author kim 2014年4月19日
 */
public class ItemAffiliationValidator implements ItemValidator {

	private final ItemStatus status = new WrongItemStatus(new ServerError().type(ProtocolType.AUTH).add(NotAllowed.DETAIL));

	private final RelationContext relationContext;

	public ItemAffiliationValidator(RelationContext relationContext) {
		super();
		this.relationContext = relationContext;
	}

	/*
	 * Item岗位必须不包含发起人的岗位(至少小于)且不为Loop
	 * 
	 * @see com.sissi.ucenter.relation.muc.validate.ItemValidator#valdate(com.sissi.context.JIDContext, com.sissi.context.JID, com.sissi.context.JID)
	 */
	@Override
	public ItemStatus valdate(JIDContext invoker, JID group, JID item) {
		return !invoker.jid().like(item) && ItemAffiliation.parse(this.relationContext.ourRelation(item, group).cast(MucRelation.class).affiliation()).contains(this.relationContext.ourRelation(invoker.jid(), group).cast(MucRelation.class).affiliation()) ? this.status : ValidItemStatus.INSTANCE;
	}
}
